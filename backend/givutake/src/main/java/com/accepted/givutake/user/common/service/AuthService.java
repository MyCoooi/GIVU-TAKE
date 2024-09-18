package com.accepted.givutake.user.common.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.model.*;
import com.accepted.givutake.user.common.repository.UserRepository;
import com.accepted.givutake.user.common.JwtTokenProvider;
import com.accepted.givutake.user.common.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenDto login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        // 1. `email + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 User 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    // 해당 이메일을 가진 회원의 비밀번호 검증
    public void verifyPassword(String email, PasswordDto passwordDto) {
        String password = passwordDto.getPassword();

        // 1. userId 기반으로 비밀번호를 가져온다
        UserDto savedUserDto = userService.getUserByEmail(email);
        String savedPassword = savedUserDto.getPassword();

        boolean isValid = passwordEncoder.matches(password, savedPassword);
        if (!isValid) {
            throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH_EXCEPTION);
        }
    }

}
