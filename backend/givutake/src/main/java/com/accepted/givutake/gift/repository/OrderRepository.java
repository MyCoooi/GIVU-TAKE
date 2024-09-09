package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.Orders;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUsers(Users user, Pageable pageable);
}
