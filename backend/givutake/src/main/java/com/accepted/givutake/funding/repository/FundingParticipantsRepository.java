package com.accepted.givutake.funding.repository;

import com.accepted.givutake.funding.entity.FundingParticipants;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FundingParticipantsRepository extends JpaRepository<FundingParticipants, Integer> {

    List<FundingParticipants> findByUsersAndCreatedDateBetween(Users users, LocalDateTime startDate, LocalDateTime endDate);
    List<FundingParticipants> findByUsers(Users users);
    List<FundingParticipants> findByCreatedDateAfter(LocalDateTime startDate);
    List<FundingParticipants> findByCreatedDateBefore(LocalDateTime endDate);
    long countByUsers(Users users);
}
