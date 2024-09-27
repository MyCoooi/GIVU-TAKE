package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.payment.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GiftReviewRepository extends JpaRepository<GiftReviews,Integer>, JpaSpecificationExecutor<GiftReviews> {
    Page<GiftReviews> findAll(Pageable pageable);
    boolean existsByOrdersAndIsDeleteFalse(Orders order);
}
