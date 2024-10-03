package com.accepted.givutake.gift.service;

import com.accepted.givutake.gift.model.GiftPercentageDto;
import com.accepted.givutake.gift.repository.GiftPercentageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class GiftStatsService {

    private final GiftPercentageRepository giftPercentageRepository;
    public GiftPercentageDto getGiftPercentage(Integer giftIdx) {
        List<Object[]> results;
        if (giftIdx == null) {
            results = giftPercentageRepository.getOverallGiftStatistics();
        } else {
            results = giftPercentageRepository.getGiftStatisticsByGiftId(giftIdx);
        }

        GiftPercentageDto giftPercentageDto = new GiftPercentageDto();
        Map<String, List<GiftPercentageDto.StatDto>> statistics = new HashMap<>();

        for (Object[] row : results) {
            String statType = (String) row[0];
            String name = (String) row[1];
            Long count = ((Number) row[2]).longValue();
            Double percentage = ((Number) row[3]).doubleValue();

            GiftPercentageDto.StatDto statDto = new GiftPercentageDto.StatDto(name, count, percentage);

            statistics.computeIfAbsent(statType, k -> new ArrayList<>()).add(statDto);
        }

        giftPercentageDto.setStatistics(statistics);
        return giftPercentageDto;
    }

}
