package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.Gifts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GiftRepository extends JpaRepository<Gifts,Integer>, JpaSpecificationExecutor<Gifts> {
    Page<Gifts> findAll(Pageable pageable); // 모두 검색
}
