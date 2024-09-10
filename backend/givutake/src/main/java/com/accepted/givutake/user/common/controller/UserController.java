package com.accepted.givutake.user.common.controller;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.client.model.AddressDto;
import com.accepted.givutake.user.common.model.*;
import com.accepted.givutake.user.common.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class  UserController {

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

    // JWT 토큰으로 회원 정보 조회
    @GetMapping
    public ResponseEntity<ResponseDto> getUserByToken(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ResponseUserDto savedUserDto = userService.getUserByEmail(email);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedUserDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // JWT 토큰으로 회원 정보 수정
    @PatchMapping
    public ResponseEntity<ResponseDto> modifyUserByToken(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ModifyUserDto modifyUserDto) {
        String email = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // 첫번 째 권한 추출
        GrantedAuthority authority = authorities.stream().findFirst()
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION));
        String role = authority.getAuthority();

        // 입력값 유효성 검사
        // 수혜자는 isMale, birth 값을 가질 수 없다
        if ("ROLE_CORPORATION".equals(role)) {
            if (modifyUserDto.getIsMale() != null) {
                throw new ApiException(ExceptionEnum.UNEXPECTED_ISMALE_EXCEPTION);
            }
            if (modifyUserDto.getBirth() != null) {
                throw new ApiException(ExceptionEnum.UNEXPECTED_BIRTH_EXCEPTION);
            }
        }

        // 사용자는 isMale, birth 값이 필수로 있어야 한다
        if ("ROLE_CLIENT".equals(role)) {
            if (modifyUserDto.getIsMale() == null) {
                throw new ApiException(ExceptionEnum.MISSING_ISMALE_EXCEPTION);
            }
            if (modifyUserDto.getBirth() == null) {
                throw new ApiException(ExceptionEnum.MISSING_BIRTH_EXCEPTION);
            }
        }

        ResponseUserDto savedUserDto = userService.modifyUserByEmail(email, modifyUserDto);

        ResponseDto responseDto = ResponseDto.builder()
                .data(savedUserDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // JWT 토큰으로 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUserByToken(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        userService.withdrawUserByEmail(email);

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
