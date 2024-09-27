package com.accepted.givutake.user.admin.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.admin.model.AdminSignUpDto;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users signUp(AdminSignUpDto adminSignUpDto) {
        // 1. 관리자 회원가입을 위한 확인 코드가 맞는지 확인
        String code = adminSignUpDto.getCode();
        if (!"싸피 11기 파이팅".equals(code)) {
            throw new ApiException(ExceptionEnum.ADMING_SIGNUP_CODE_MISMATCH_EXCEPTION);
        }

        // 2. 이메일 중복 검사
        String email  = adminSignUpDto.getEmail();
        this.emailDuplicatedCheck(email);

        // 3. 비밀번호 암호화
        String password = adminSignUpDto.getPassword();
        adminSignUpDto.setPassword(passwordEncoder.encode(password));

        // 4. DB에 회원 정보 저장
        return usersRepository.save(adminSignUpDto.toEntity());
    }

    // 이메일 중복 검사
    public void emailDuplicatedCheck(String email) {
        Optional<Users> optionalExistingUsers =  usersRepository.findByEmail(email);

        if (!optionalExistingUsers.isEmpty()) {
            Users savedUser = optionalExistingUsers.get();

            // 이미 탈퇴한 회원일 경우 회원 정보 조회 불가
            if (savedUser.isWithdraw()) {
                throw new ApiException(ExceptionEnum.USER_ALREADY_WITHDRAWN_EXCEPTION);
            }
            else {
                throw new ApiException(ExceptionEnum.DUPLICATED_EMAIL_EXCEPTION);
            }
        }
    }

    // 조건에 맞는 수혜자 정보 조회
    public List<Users> getCorporation(Character isApproved, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (isApproved == null) {
            List<Roles> roles = Arrays.asList(Roles.ROLE_CORPORATION, Roles.ROLE_CORPORATIONYET);
            return usersRepository.findByRolesIn(roles, pageable).getContent();
        }
        if (isApproved == 'Y') {
            return usersRepository.findByRoles(Roles.ROLE_CORPORATION, pageable).getContent();
        }
        else if (isApproved == 'N') {
            return usersRepository.findByRoles(Roles.ROLE_CORPORATIONYET, pageable).getContent();
        }
        else {
            return new ArrayList<>();
        }


    }
}
