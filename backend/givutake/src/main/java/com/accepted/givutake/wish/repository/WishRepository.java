package com.accepted.givutake.wish.repository;

import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.wish.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Integer> {
    Page<Wish> findByUsers(Users user, Pageable pageable);
    Optional<Wish> findByUsersAndGift(Users user, Gifts gift);
}
