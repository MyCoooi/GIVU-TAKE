package com.accepted.givutake.user.common.controller;

import ch.qos.logback.core.CoreConstants;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.client.model.AddressDto;
import com.accepted.givutake.user.common.model.CompositionSignUpDto;
import com.accepted.givutake.user.common.model.LoginDto;
import com.accepted.givutake.user.common.model.SignUpDto;
import com.accepted.givutake.user.common.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 이메일 유저 회원가입
    @PostMapping
    public ResponseEntity<ResponseDto> emailSignUp(@Valid @RequestBody CompositionSignUpDto compositionSignUpDto) {

        SignUpDto signUpDto = compositionSignUpDto.getSignUpDto();
        AddressDto addressDto = compositionSignUpDto.getAddressDto();

        userService.emailSignUp(signUpDto, addressDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
