package com.accepted.givutake.user.jwt.repository;

import com.accepted.givutake.user.jwt.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Integer> {
    Optional<RefreshTokenEntity> findByUserIdx(int userIdx);
}
