package com.accepted.givutake.user.common.controller;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.common.JwtTokenProvider;
import com.accepted.givutake.user.common.model.*;
import com.accepted.givutake.user.common.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인
    @PostMapping
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        JwtTokenDto jwtToken = authService.login(loginDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(jwtToken)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // JWT 토큰으로 비밀번호 확인
    @PostMapping("/password/verification")
    public ResponseEntity<ResponseDto> verifyPassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PasswordDto passwordDto) {
        String email = userDetails.getUsername();

        authService.verifyPassword(email, passwordDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // refresh 토큰으로 acccess token, refresh token 재발급
    @GetMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(HttpServletRequest httpRequest) {
        // Request Header에서 토큰 정보 추출
        String token = jwtTokenProvider.resolveTokenFromRequestHeader(httpRequest);

        JwtTokenDto newJwtToken = authService.reissueToken(token);
        ResponseDto responseDto = ResponseDto.builder()
                .data(newJwtToken)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
