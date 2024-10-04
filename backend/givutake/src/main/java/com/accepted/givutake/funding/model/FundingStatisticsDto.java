package com.accepted.givutake.funding.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingStatisticsDto {
    private FundingDayStatisticDto fundingDayStatistic;
    private FundingParticipateDto fundingParticipate;
    private FundingStatisticsDto fundingStatistics;
}
