package com.accepted.givutake.user.client.repository;

import com.accepted.givutake.user.client.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Integer> {
    List<Addresses> findByUserIdxAndIsDeletedFalseOrderByIsRepresentativeDesc(int userIdx);
    Optional<Addresses> findByUserIdxAndIsDeletedFalseAndIsRepresentativeTrue(int userIdx);
    Optional<Addresses> findByAddressIdx(int addressIdx);
    long countByUserIdx(int userIdx);

    @Modifying
    @Query("UPDATE Addresses a SET a.isRepresentative = false WHERE a.userIdx = :userIdx AND a.isRepresentative = true")
    void updateRepresentativeStatusFalse(@Param("userIdx") int userIdx);

    @Query("SELECT a.isRepresentative FROM Addresses a WHERE a.addressIdx = :addressIdx")
    Boolean findIsRepresentativeByAddressIdx(@Param("addressIdx") int addressIdx);
}
