package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.model.FundingAddDto;
import com.accepted.givutake.funding.model.FundingReviewAddDto;
import com.accepted.givutake.funding.repository.FundingRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final UserService userService;

    // fundingIdx에 해당하는 펀딩 조회
    public Fundings getFundingByFundingIdx(int fundingIdx) {
        Optional<Fundings> optionalExistingFundings =  fundingRepository.findByFundingIdx(fundingIdx);

        if (!optionalExistingFundings.isEmpty()) {
            Fundings savedFundings = optionalExistingFundings.get();

            // 이미 삭제된 펀딩일 경우 조회 불가
            if (savedFundings.isDeleted()) {
                throw new ApiException(ExceptionEnum.FUNDING_ALREADY_DELETED_EXCEPTION);
            }

            return savedFundings;
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION);
        }
    }

    // 아이디가 email인 유저의 펀딩 추가
    public Fundings addFundingByEmail(String email, FundingAddDto fundingAddDto) {
        // 1. DB에서 user 조회하기
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. DB에 저장
        return fundingRepository.save(fundingAddDto.toEntity(savedUsers));
    }

}
