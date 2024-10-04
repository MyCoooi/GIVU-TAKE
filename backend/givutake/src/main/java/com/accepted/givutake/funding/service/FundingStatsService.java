package com.accepted.givutake.funding.service;

import com.accepted.givutake.funding.entity.Fundings;
import com.accepted.givutake.funding.model.FundingStatsByAgeAndGenderDto;
import com.accepted.givutake.funding.repository.FundingRepository;
import com.accepted.givutake.funding.repository.FundingStatisticsRepository;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FundingStatsService {

    private final FundingStatisticsRepository fundingStatisticsRepository;
    private final FundingRepository fundingRepository;

    public void validateFunding(int fundingIdx) {
        Optional<Fundings> funding = fundingRepository.findByFundingIdx(fundingIdx);

        Fundings f = funding.orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_FUNDING_WITH_IDX_EXCEPTION));
    }

    public FundingStatsByAgeAndGenderDto getFundingCountByAgeAndGender(int fundingIdx) {

        List<Object[]> results = fundingStatisticsRepository.getFundingStatsByAgeAndGender(fundingIdx);

        FundingStatsByAgeAndGenderDto result = new FundingStatsByAgeAndGenderDto();

        HashMap<String, Long> maleData = new HashMap<>();
        HashMap<String, Long> femaleData = new HashMap<>();

        String[] ageGroups = { "20s", "30s", "40s", "50s", "60+" };

        for (String ageGroup : ageGroups) {
            maleData.put(ageGroup, 0L);
            femaleData.put(ageGroup, 0L);
        }

        results.forEach(row -> {
                    String gender = (String) row[0];
                    String ageGroup = (String) row[1];
                    Long count = ((Number) row[2]).longValue();

                    if ("male".equals(gender)) {
                        maleData.put(ageGroup, count);
                    } else {
                        femaleData.put(ageGroup, count);
                    }
                }
        );

        result.setMaleData(maleData);
        result.setFemaleData(femaleData);

        return result;
    }
}
