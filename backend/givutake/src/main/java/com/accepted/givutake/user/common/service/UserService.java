package com.accepted.givutake.user.common.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.repository.RegionRepository;
import com.accepted.givutake.user.client.entity.Addresses;
import com.accepted.givutake.user.client.model.AddressDto;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.model.ModifyUserDto;
import com.accepted.givutake.user.common.model.ResponseUserDto;
import com.accepted.givutake.user.common.model.SignUpDto;
import com.accepted.givutake.user.client.repository.AddressRepository;
import com.accepted.givutake.user.common.repository.UserRepository;

import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public UserService(UserRepository userRepository, RegionRepository regionRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;

        // ValidatorFactory와 Validator 초기화
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // 이메일 사용자 회원가입
    @Transactional
    public void emailSignUp(SignUpDto signUpDto, AddressDto addressDto) {
        Roles role = signUpDto.getRoles();

        // 유효하지 않은 권한정보가 들어온 경우
        if (!(role == Roles.ROLE_CLIENT || role == Roles.ROLE_CORPORATION)) {
            throw new AccessDeniedException("권한 정보가 유효하지 않습니다.");
        }

        // 이메일 중복 검사
        this.emailDuplicatedCheck(signUpDto.getEmail());

        // 비밀번호 암호화
        signUpDto.setPassword(this.encode(signUpDto.getPassword()));

        // ref) 관리자는 회원가입할 수 없다. DB를 통해 직접 데이터 추가 요망.
        // 1. 수혜자 회원가입 관련 입력값 검증 및 처리
        if (role == Roles.ROLE_CORPORATION) {
            // 주소값은 들어오면 안된다
            if (addressDto != null) {
                throw new ApiException(ExceptionEnum.UNEXPECTED_REPRESENTATIVE_ADDRESS_EXCEPTION);
            }
            // 수혜자 회원가입 유효성 검증
            checkArgumentValidityForCorporationSignUp(signUpDto);
            // DB에 회원 정보 저장
            userRepository.save(signUpDto.toEntity());
        }
        // 2. 사용자 회원가입 관련 입력값 검증 및 처리
        else {
            // 대표 주소는 필수 입력 값
            if (addressDto == null) {
                throw new ApiException(ExceptionEnum.MISSING_REPRESENTATIVE_ADDRESS_EXCEPTION);
            }
            // 사용자 회원가입 유효성 검증
            checkArgumentValidityForClientSignUp(signUpDto);
            // 대표 주소 DTO의 유효성을 수동으로 검증
            validateAddressDto(addressDto);

            // DB에 회원 정보 저장
            Users savedUser = userRepository.save(signUpDto.toEntity());
            // DB에 주소 정보 저장
            Addresses savedAddress = addressRepository.save(addressDto.toEntity(savedUser.getUserIdx()));
            // 대표 주소로 지정
            userRepository.updateAddressIdxByUserIdx(savedUser.getUserIdx(), savedAddress.getAddressIdx());
        }
    }

    // 수혜자 회원가입 유효성 검증
    public void checkArgumentValidityForCorporationSignUp(SignUpDto signUpDto) {
        // 수혜자는 isMale, birth 값을 가지면 안된다.
        if (signUpDto.getIsMale() != null) {
            throw new ApiException(ExceptionEnum.UNEXPECTED_ISMALE_EXCEPTION);
        }
        if (signUpDto.getBirth() != null) {
            throw new ApiException(ExceptionEnum.UNEXPECTED_BIRTH_EXCEPTION);
        }

        // regionIdx 값은 필수이며, 유효해야 한다.
        if (signUpDto.getRegionIdx() == null) {
            throw new ApiException(ExceptionEnum.MISSING_REGION_EXCEPTION);
        }
        else if (!regionRepository.existsByRegionIdx(signUpDto.getRegionIdx())) {
            throw new ApiException(ExceptionEnum.ILLEGAL_REGION_EXCEPTION);
        }
    }

    // 사용자 회원가입 유효성 검증
    public void checkArgumentValidityForClientSignUp(SignUpDto signUpDto) {
        // 사용자는 regionIdx 값을 가지면 안된다.
        if (signUpDto.getRegionIdx() != null) {
            throw new ApiException(ExceptionEnum.UNEXPECTED_REGION_EXCEPTION);
        }

        // isMale, birth 값은 필수이다.
        if (signUpDto.getIsMale() == null) {
            throw new ApiException(ExceptionEnum.MISSING_ISMALE_EXCEPTION);
        }
        if (signUpDto.getBirth() == null) {
            throw new ApiException(ExceptionEnum.MISSING_BIRTH_EXCEPTION);
        }

    }

    // 대표 주소 DTO의 유효성을 수동으로 검증
    public void validateAddressDto(AddressDto addressDto) {
        // 1. AddressDto 객체의 유효성 검사 수행
        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        // 2. 유효성 검증 결과에서 오류가 있는지 확인
        if (!violations.isEmpty()) {
            // 3. 유효성 검증 실패 시 ConstraintViolationException 발생
            throw new ConstraintViolationException(violations);
        }
    }

    // 이메일 중복 검사
    public void emailDuplicatedCheck(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ApiException(ExceptionEnum.DUPLICATED_EMAIL_EXCEPTION);
        }
    }

    // 비밀번호 암호화
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    // JWT 토큰으로 회원 정보 조회
    public ResponseUserDto getUserByEmail(String email) {
        Optional<Users> optionalExistingUsers =  userRepository.findByEmail(email);

        if (!optionalExistingUsers.isEmpty()) {
            Users savedUser = optionalExistingUsers.get();

            // 이미 탈퇴한 회원일 경우 회원 정보 조회 불가
            if (savedUser.isWithdraw()) {
                throw new ApiException(ExceptionEnum.USER_ALREADY_WITHDRAWN_EXCEPTION);
            }

            return ResponseUserDto.toDto(optionalExistingUsers.get());
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION);
        }
    }

    // JWT 토큰으로 회원 정보 수정
    public ResponseUserDto modifyUserByEmail(String email, ModifyUserDto modifyUserDto) {
        Optional<Users> optionalExistingUsers = userRepository.findByEmail(email);

        if (!optionalExistingUsers.isEmpty()) {
            Users savedUser = optionalExistingUsers.get();

            // 이미 탈퇴한 회원일 경우 회원 정보 수정 불가
            if (savedUser.isWithdraw()) {
                throw new ApiException(ExceptionEnum.USER_ALREADY_WITHDRAWN_EXCEPTION);
            }

            // 사용자 정보 수정
            savedUser.setName(modifyUserDto.getName());
            savedUser.setIsMale(modifyUserDto.getIsMale());
            savedUser.setBirth(modifyUserDto.getBirth());
            savedUser.setMobilePhone(modifyUserDto.getMobilePhone());
            savedUser.setLandlinePhone(modifyUserDto.getLandlinePhone());
            savedUser.setProfileImageUrl(modifyUserDto.getProfileImageUrl());

            // 변경된 정보 저장
            Users modifiedUser = userRepository.save(savedUser);

            return ResponseUserDto.toDto(modifiedUser);
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION);
        }
    }

    // JWT 토큰으로 회원 탈퇴
    public void withdrawUserByEmail(String email) {
        Optional<Users> optionalExistingUsers = userRepository.findByEmail(email);

        if (!optionalExistingUsers.isEmpty()) {
            Users savedUser = optionalExistingUsers.get();

            // 이미 탈퇴한 회원일 경우 회원 탈퇴 불가
            if (savedUser.isWithdraw()) {
                throw new ApiException(ExceptionEnum.USER_ALREADY_WITHDRAWN_EXCEPTION);
            }

            // 관리자는 탈퇴 불가
            // ref) 관리자는 DB에 직접 접근해서 탈퇴 요망.
            if (savedUser.getRoles() == Roles.ROLE_ADMIN) {
                throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
            }

            userRepository.updateIsWithdrawByEmail(email, true);
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION);
        }
    }

}
