package com.accepted.givutake.funding.repository;

import com.accepted.givutake.funding.entity.FundingParticipants;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FundingParticipantsRepository extends JpaRepository<FundingParticipants, Long> {

    List<FundingParticipants> findByUsersAndCreatedDateBetween(Users users, LocalDateTime startDate, LocalDateTime endDate);
    List<FundingParticipants> findByUsers(Users users);
    List<FundingParticipants> findByCreatedDateAfter(LocalDateTime startDate);
    List<FundingParticipants> findByCreatedDateBefore(LocalDateTime endDate);
    long countByUsers(Users users);

    @Query("SELECT SUM(fp.fundingFee) FROM FundingParticipants fp WHERE fp.users.userIdx = :userIdx")
    Integer sumFundingFeeByUserIdx(@Param("userIdx") int userIdx);
}
