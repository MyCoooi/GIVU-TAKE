package com.accepted.givutake.global.repository;

import com.accepted.givutake.global.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByRegionIdx(int regionIdx);

    @Query("SELECT r.regionIdx FROM Region r WHERE r.sido LIKE CONCAT(:sido, '%') AND r.sigungu = :sigungu")
    Integer findRegionIdxBySidoAndSigungu(@Param("sido") String sido, @Param("sigungu") String sigungu);
}