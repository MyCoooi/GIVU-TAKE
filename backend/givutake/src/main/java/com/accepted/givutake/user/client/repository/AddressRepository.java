package com.accepted.givutake.user.client.repository;

import com.accepted.givutake.user.client.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Integer> {
    boolean existsByAddressIdx(int addressIdx);

}
