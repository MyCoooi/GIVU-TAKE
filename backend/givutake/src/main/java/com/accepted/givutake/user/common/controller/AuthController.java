package com.accepted.givutake.user.common.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.common.model.LoginDto;
import com.accepted.givutake.user.common.service.AuthService;
import com.accepted.givutake.user.common.model.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseDto> login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        JwtTokenDto jwtToken = authService.login(email, password);
        log.info("[AuthController] login request: email = {}", email);

        ResponseDto responseDto = ResponseDto.builder()
                .data(jwtToken)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
