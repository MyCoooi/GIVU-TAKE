package com.accepted.givutake.user.common.repository;

import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.addressIdx = :addressIdx WHERE u.userIdx = :userIdx")
    void updateAddressIdxByUserIdx(@Param("userIdx") int userIdx, @Param("addressIdx") int addressIdx);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.isWithdraw = :isWithdraw WHERE u.email = :email")
    void updateIsWithdrawByEmail(@Param("email") String email, @Param("isWithdraw") boolean isWithdraw);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.password = :password WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

}