package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.model.FundingStatsByAgeAndGenderDto;
import com.accepted.givutake.funding.repository.FundingStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FundingStatsService {

    private final FundingStatisticsRepository fundingStatisticsRepository;

    public FundingStatsByAgeAndGenderDto getFundingCountByAgeAndGender(int fundingIdx) {

        List<Object[]> results = fundingStatisticsRepository.getFundingStatsByAgeAndGender(fundingIdx);

        Map<String, List<FundingStatsByAgeAndGenderDto.AgeGroupData>> groupedData = results.stream()
                .map(row -> new Object[]{
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).intValue()
                })
                .collect(Collectors.groupingBy(
                        row -> (String) row[0],
                        Collectors.mapping(
                                row -> new FundingStatsByAgeAndGenderDto.AgeGroupData((String) row[1], ((Number) row[2]).intValue()),
                                Collectors.toList()
                        )
                ));

        return FundingStatsByAgeAndGenderDto.builder()
                .maleData(groupedData.getOrDefault("male", List.of()))
                .femaleData(groupedData.getOrDefault("female", List.of()))
                .build();
    }
}
