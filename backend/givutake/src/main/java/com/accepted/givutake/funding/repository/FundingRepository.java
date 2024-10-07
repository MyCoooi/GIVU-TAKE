package com.accepted.givutake.funding.repository;

import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface FundingRepository extends JpaRepository<Fundings, Integer> {
    Optional<Fundings> findByFundingIdx(int fundingIdx);
    List<Fundings> findByFundingTypeAndStateAndIsDeletedFalse(char fundingType, byte state);
    List<Fundings> findByIsDeletedFalseAndState(byte state);
    Page<Fundings> findByCorporation(Users corporation, Pageable pageable);
    List<Fundings> findTop10ByOrderByEndDate();

    @Modifying
    @Transactional
    @Query("UPDATE Fundings f SET f.isDeleted = true WHERE f.fundingIdx = :fundingIdx")
    int updateIsDeletedTrueByFundingIdx(@Param("fundingIdx") int fundingIdx);
}
