package com.accepted.givutake.user.admin.controller;

import com.accepted.givutake.global.model.ResponseDto;
import com.accepted.givutake.user.admin.model.AdminSignUpDto;
import com.accepted.givutake.user.admin.model.AdminUserViewDto;
import com.accepted.givutake.user.admin.service.AdminService;
import com.accepted.givutake.user.common.entity.Users;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // 관리자 회원가입
    @PostMapping
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody AdminSignUpDto adminSignUpDto) {
        Users savedUsers = adminService.signUp(adminSignUpDto);
        AdminUserViewDto adminUserViewDto = AdminUserViewDto.toDto(savedUsers);

        ResponseDto responseDto = ResponseDto.builder()
                .data(adminUserViewDto)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
