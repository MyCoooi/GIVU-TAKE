package com.accepted.givutake.user.common;
import com.accepted.givutake.user.common.entity.RefreshTokenEntity;
import com.accepted.givutake.user.common.model.JwtTokenDto;
import com.accepted.givutake.user.common.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;


import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    // 900000 == 15분
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 900000;
    // 604800000 == 7일
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 604800000;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RefreshTokenRepository refreshTokenRepository) {
        // 생성자 주입 방식
        this.refreshTokenRepository = refreshTokenRepository;
        // secretkey를 base64로 디코딩
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // hmac에서 사용할 수 있는 Key 객체로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // User 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtTokenDto generateToken(Authentication authentication) {
        // 1. 시간 설정
        long nowMillis = System.currentTimeMillis();

        // 2. Access Token 생성
        String accessToken = generateAccessToken(authentication, nowMillis, ACCESS_TOKEN_EXPIRATION_TIME);

        // 3. Refresh Token 생성 후 저장
        String email = authentication.getName();
        String refreshToken = generateRefreshToken(email, nowMillis, REFRESH_TOKEN_EXPIRATION_TIME);
        saveRefreshToken(email, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);

        return JwtTokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Refresh Token을 Redis에 저장
    public void saveRefreshToken(String email, String refreshToken, long expirationTime) {
        long seconds = expirationTime / 1000; // 초 단위로 바꿈
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                                                        .email(email)
                                                        .refreshToken(refreshToken)
                                                        .ttl(seconds)
                                                        .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    // Access Token 생성
    public String generateAccessToken(Authentication authentication, long nowMillis, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        claims.put("auth", authorities);
        return createToken(claims, authentication.getName(), nowMillis, expirationTime);
    }

    // Refresh Token 생성
    public String generateRefreshToken(String email, long nowMillis, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email, nowMillis, expirationTime);
    }

    // 전달받은 파라미터로부터 토큰 생성
    public String createToken(Map<String, Object> claims, String email, long nowMillis, long expirationTime) {
        String ISSUER = "com.accepted.givutake";
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expirationTime);

        return Jwts.builder()
                .claims(claims) // 기타 정보
                .subject(email) // 사용자 식별자
                .issuer(ISSUER) // 토큰 발행자
                .notBefore(now) // 활성화 시간
                .issuedAt(now) // 발행 시간
                .expiration(exp) // 만료 시간
                .setId(UUID.randomUUID().toString()) // 고유 식별자
                .signWith(key, SignatureAlgorithm.HS512) // 서명 알고리즘 및 키
                .compact();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String token) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new AccessDeniedException("권한 정보가 없는 토큰 입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.error("Not expected JWT Token format", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        } catch (Exception e) {
            log.error("Internal server error.", e);
        }
        return false;
    }

    // jwt 토큰 복호화
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Request Header에서 토큰 정보 추출
    public String resolveTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    // Request Parameter에서 토큰 정보 추출
//    public String resolveTokenFromRequestParam(HttpServletRequest request) {
//        String token = request.getParameter("token");
//        return token;
//    }
}
