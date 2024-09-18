package com.accepted.givutake.user.common.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.common.model.LoginDto;
import com.accepted.givutake.user.common.model.PasswordDto;
import com.accepted.givutake.user.common.service.AuthService;
import com.accepted.givutake.user.common.model.JwtTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        JwtTokenDto jwtToken = authService.login(loginDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(jwtToken)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 비밀번호 확인
    @PostMapping("/password/verification")
    public ResponseEntity<ResponseDto> verifyPassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PasswordDto passwordDto) {
        String email = userDetails.getUsername();

        authService.verifyPassword(email, passwordDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
