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
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.addressIdx = :addressIdx WHERE u.userIdx = :userIdx")
    int updateAddressIdx(@Param("userId") int userIdx, @Param("addressIdx") int addressIdx);

}