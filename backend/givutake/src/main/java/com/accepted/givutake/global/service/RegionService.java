package com.accepted.givutake.global.service;

import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;

    public int getRegionIdxBySidoAndSigungu(String sido, String sigungu) {
        Integer regionIdx = regionRepository.findRegionIdxBySidoAndSigungu(sido, sigungu);

        if (regionIdx == null) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_REGION_IDX_EXCEPTION);
        }

        return regionIdx;
    }

    public boolean existsByRegionIdx(int regionIdx) {
        return regionRepository.existsByRegionIdx(regionIdx);
    }
}
