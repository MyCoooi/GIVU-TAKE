package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GiftReviewRepository extends JpaRepository<GiftReviews,Integer>, JpaSpecificationExecutor<GiftReviews> {
    Page<GiftReviews> findAll(Pageable pageable);
}
