package com.accepted.givutake.user.client.repository;

import com.accepted.givutake.user.client.entity.Cards;
import com.accepted.givutake.user.common.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Integer> {

    boolean existsByCardNumber(String cardNumber);
    Optional<Cards> findByUsersAndIsRepresentativeTrue(Users users);
}
