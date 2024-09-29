package com.accepted.givutake.payment.repository;

import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.payment.entity.Orders;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUsers(Users user, Pageable pageable);
    int countByGift(Gifts gift);
    @Query("SELECT SUM(o.price) FROM Orders o")
    Integer getTotalOrderPrice();

    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.users.userIdx = :userIdx")
    Integer sumPriceByUserIdx(@Param("userIdx") int userIdx);
}
