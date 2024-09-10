package com.accepted.givutake.global.repository;

import com.accepted.givutake.global.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByRegionIdx(int regionIdx);
}