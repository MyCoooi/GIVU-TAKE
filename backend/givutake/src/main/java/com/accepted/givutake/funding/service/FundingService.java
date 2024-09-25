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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // email 사용자의 펀딩 추가
    public Fundings addFundingByEmail(String email, FundingAddDto fundingAddDto) {
        // 1. DB에서 user 조회하기
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. state 값 지정
        byte state = this.calculateState(fundingAddDto.getStartDate());

        // 3. DB에 저장
        return fundingRepository.save(fundingAddDto.toEntity(savedUsers, state));
    }

    // 현재 시간과 모금 시작일을 비교하여 상태값 반환
    public byte calculateState(LocalDate startDate) {
        LocalDate curDate = LocalDate.now();
        if (startDate.isAfter(curDate)) {
            return 0; // 대기 상태
        }
        return 1; // 모금 진행 중 상태
    }

    // fundingIdx에 해당하는 펀딩 삭제
    public Fundings deleteFundingByFundingIdx(String email, int fundingIdx) {
        // 1. user 정보 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 펀딩 정보 조회
        Fundings savedFundings = this.getFundingByFundingIdx(fundingIdx);

        // 3. 펀딩을 등록한 사람과 펀딩을 삭제하려는 사람이 일치하는지 확인
        if (savedUsers.getUserIdx() != savedFundings.getCorporation().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 삭제하려는 펀딩이 모금 종료되었거나 진행 중이라면 삭제 불가
        if (savedFundings.getState() == (byte) 2) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_DONE_FUNDING_DELETION_EXCEPTION);
        }

        if (savedFundings.getState() == (byte) 1) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_FUNDING_IN_PROGRESS_DELETION_EXCEPTION);
        }

        // 5. 삭제
        int cnt = fundingRepository.updateIsDeletedTrueByFundingIdx(fundingIdx);

        if (cnt == 0) {
            throw new ApiException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }

        savedFundings.setDeleted(true);
        return savedFundings;
    }

}
