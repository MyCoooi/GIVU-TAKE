package com.accepted.givutake.global.repository;

import com.accepted.givutake.global.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByRegionIdx(int regionIdx);

    @Query("SELECT r.regionIdx FROM Region r WHERE r.regionName LIKE CONCAT(:sido, '%')")
    Integer findRegionIdxBySido(@Param("sido") String sido);
}