package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.FundingParticipants;
import com.accepted.givutake.funding.repository.FundingParticipantsRepository;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.UserDto;
import com.accepted.givutake.user.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FundingParticipantService {

    private final FundingParticipantsRepository fundingParticipantsRepository;
    private final UserService userService;

    // 일정 기간 동안의 자신의 펀딩 내역 조회
    public List<FundingParticipants> getFundingParticipantsListByEmail(String email, LocalDate startDate, LocalDate endDate) {
        // 1. user 조회
        UserDto savedUserDto = userService.getUserByEmail(email);
        Users savedUsers = savedUserDto.toEntity();

        // 2. startDate, endDate를 LocalDateTime으로 변경
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        // 2. startDate와 endDate 값이 빈 값일 경우 전체조회
        if (endDate == null && startDate == null) {
            return fundingParticipantsRepository.findByUsers(savedUsers);
        }

        if (startDate == null) {
            endDateTime = endDate.atTime(23, 59, 59);  // 해당 날짜의 23:59:59
            return fundingParticipantsRepository.findByCreatedDateBefore(endDateTime);
        }

        if (endDate == null) {
            startDateTime = startDate.atStartOfDay();
            return fundingParticipantsRepository.findByCreatedDateAfter(startDateTime);
        }

        return fundingParticipantsRepository.findByUsersAndCreatedDateBetween(savedUsers, startDateTime, endDateTime);
    }
}
