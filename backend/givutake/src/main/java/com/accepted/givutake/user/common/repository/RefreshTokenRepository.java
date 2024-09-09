package com.accepted.givutake.user.common.repository;

import com.accepted.givutake.user.common.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByEmail(String email);
}
