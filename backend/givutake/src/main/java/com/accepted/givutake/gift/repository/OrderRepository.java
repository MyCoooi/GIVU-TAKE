package com.accepted.givutake.gift.repository;

import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.gift.entity.Orders;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUsers(Users user, Pageable pageable);
    int countByGift(Gifts gift);
    @Query("SELECT SUM(o.price) FROM Orders o")
    Integer getTotalOrderPrice();
}
