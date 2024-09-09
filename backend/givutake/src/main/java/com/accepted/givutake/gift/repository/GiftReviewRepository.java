package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.GiftReviews;
import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftReviewRepository extends JpaRepository<GiftReviews,Integer> {
    Page<GiftReviews> findByGifts(Gifts gift, Pageable pageable);
    Page<GiftReviews> findByUsers(Users user, Pageable pageable);
}
