package com.accepted.givutake.payment.repository;

import com.accepted.givutake.gift.entity.Gifts;
import com.accepted.givutake.payment.entity.Orders;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUsers(Users user, Pageable pageable);
    List<Orders> findByUsers(Users users);
    List<Orders> findByUsersAndCreatedDateBefore(Users users, LocalDateTime endDateTime);
    List<Orders> findByUsersAndCreatedDateAfter(Users users, LocalDateTime startDateTime);
    List<Orders> findByUsersAndCreatedDateBetween(Users users, LocalDateTime startDateTime, LocalDateTime endDateTime);
    int countByGift(Gifts gift);
    @Query("SELECT SUM(o.price) FROM Orders o")
    Integer getTotalOrderPrice();

    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.users.userIdx = :userIdx")
    Integer sumPriceByUserIdx(@Param("userIdx") int userIdx);

}
