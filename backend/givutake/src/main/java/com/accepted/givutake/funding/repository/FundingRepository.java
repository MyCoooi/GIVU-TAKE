package com.accepted.givutake.funding.repository;

import com.accepted.givutake.funding.entity.Fundings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundingRepository extends JpaRepository<Fundings, Integer> {
    Optional<Fundings> findByFundingIdx(int fundingIdx);
}
