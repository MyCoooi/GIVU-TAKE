package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.repository.FundingRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;

    public Fundings getFundingByFundingIdx(int fundingIdx) {
        Optional<Fundings> optionalExistingFundings =  fundingRepository.findByFundingIdx(fundingIdx);

        if (!optionalExistingFundings.isEmpty()) {
            Fundings savedFundings = optionalExistingFundings.get();

            // 이미 탈퇴한 회원일 경우 회원 정보 조회 불가
            if (savedFundings.isDeleted()) {
                throw new ApiException(ExceptionEnum.FUNDING_ALREADY_DELETED_EXCEPTION);
            }

            return savedFundings;
        }
        else {
            throw new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION);
        }
    }
}
