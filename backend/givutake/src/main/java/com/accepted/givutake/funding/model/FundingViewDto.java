package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.Fundings;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingViewDto {

    private int fundingIdx;
    private String sido;
    private String sigungu;
    private String fundingTitle;
    private int goalMoney;
    private int totalMoney;
    private LocalDate startDate;
    private LocalDate endDate;
    private String fundingThumbnail;

    public static FundingViewDto toDto(Fundings fundings) {
        return FundingViewDto.builder()
                .fundingIdx(fundings.getFundingIdx())
                .sido(fundings.getCorporation().getRegion().getSido())
                .sigungu(fundings.getCorporation().getRegion().getSigungu())
                .fundingTitle(fundings.getFundingTitle())
                .goalMoney(fundings.getGoalMoney())
                .totalMoney(fundings.getTotalMoney())
                .startDate(fundings.getStartDate())
                .endDate(fundings.getEndDate())
                .fundingThumbnail(fundings.getFundingThumbnail())
                .build();
    }
}
