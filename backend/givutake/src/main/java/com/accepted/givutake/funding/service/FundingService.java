package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.model.*;
import com.accepted.givutake.funding.repository.FundingRepository;
import com.accepted.givutake.gift.model.purchaser;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.payment.entity.FundingParticipants;
import com.accepted.givutake.payment.repository.FundingParticipantsRepository;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.repository.UsersRepository;
import com.accepted.givutake.user.common.service.UserService;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;
    private final UsersRepository userRepository;
    private final UserService userService;
    private final FundingParticipantsRepository fundingParticipantsRepository;

    // 조건에 해당하는 모든 펀딩 조회(삭제된 펀딩은 조회 불가)
    public List<Fundings> getFundingByTypeAndState(char fundingType, byte state) {
        return fundingRepository.findByFundingTypeAndStateAndIsDeletedFalse(fundingType, state);
    }

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

    // fundingIdx에 해당하는 펀딩 수정
    public Fundings modifyFundingByFundingIdx(String email, int fundingIdx, FundingAddDto fundingAddDto) {
        // 1. user 정보 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. 펀딩 정보 조회
        Fundings savedFundings = this.getFundingByFundingIdx(fundingIdx);

        // 3. 펀딩을 등록한 사람과 펀딩을 수정하려는 사람이 일치하는지 확인
        if (savedUsers.getUserIdx() != savedFundings.getCorporation().getUserIdx()) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }

        // 4. 수정하려는 펀딩이 모금 종료되었거나 진행 중이라면 수정 불가
        if (savedFundings.getState() == (byte) 2) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_DONE_FUNDING_MODIFICATION_EXCEPTION);
        }

        if (savedFundings.getState() == (byte) 1) {
            throw new ApiException(ExceptionEnum.NOT_ALLOWED_FUNDING_IN_PROGRESS_MODIFICATION_EXCEPTION);
        }


        // 5. 수정
        // state 값 지정
        byte state = this.calculateState(fundingAddDto.getStartDate());

        savedFundings.setState(state);
        savedFundings.setFundingTitle(fundingAddDto.getFundingTitle());
        savedFundings.setFundingContent(fundingAddDto.getFundingContent());
        savedFundings.setGoalMoney(fundingAddDto.getGoalMoney());
        savedFundings.setStartDate(fundingAddDto.getStartDate());
        savedFundings.setEndDate(fundingAddDto.getEndDate());
        savedFundings.setFundingThumbnail(fundingAddDto.getFundingThumbnail());
        savedFundings.setFundingType(fundingAddDto.getFundingType());

        // 6. DB에 저장
        return fundingRepository.save(savedFundings);
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

    public FundingDayStatisticDto getFundingDayStatisticByFundingIdx(String email, int fundingIdx) {
        Fundings funding = fundingRepository.findByFundingIdx(fundingIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION));
        if(!funding.getCorporation().getEmail().equals(email)) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        LocalDate startDate = funding.getStartDate();
        LocalDate endDate = funding.getEndDate();
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int[] arr = new int[days];


        List<FundingParticipants> participants = funding.getFundingParticipantsList();


        for (FundingParticipants participant : participants) {
            LocalDate participationDate = participant.getCreatedDate().toLocalDate();
            if (!participationDate.isBefore(startDate) && !participationDate.isAfter(endDate)) {
                int dayIndex = (int) ChronoUnit.DAYS.between(startDate, participationDate);
                arr[dayIndex] += participant.getFundingFee();
            }
        }
        return new FundingDayStatisticDto(arr);
    }

    public FundingParticipateDto getFundingParticipateByFundingIdx(String email, int fundingIdx) {
        Fundings funding = fundingRepository.findByFundingIdx(fundingIdx).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION));
        if(!funding.getCorporation().getEmail().equals(email)) {
            throw new ApiException(ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        }
        List<Object[]> participateData = fundingParticipantsRepository.findFundingParticipantsByFundingIdx(fundingIdx);

        List<participant> participants= participateData.stream()
                .map(data -> new participant(
                        (String) data[0],
                        ((Number) data[1]).intValue()
                ))
                .sorted(Comparator.comparingInt(participant::getPrice).reversed())
                .collect(Collectors.toList());

        return new FundingParticipateDto(participants);
    }

}
