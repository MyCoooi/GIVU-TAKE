package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.Fundings;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FundingDetailViewDto extends FundingViewDto {

    private String fundingContent;

    public static FundingDetailViewDto toDto(Fundings fundings) {
        return FundingDetailViewDto.builder()
                .fundingIdx(fundings.getFundingIdx())
                .sido(fundings.getCorporation().getRegion().getSido())
                .sigungu(fundings.getCorporation().getRegion().getSigungu())
                .fundingTitle(fundings.getFundingTitle())
                .fundingContent(fundings.getFundingContent())
                .goalMoney(fundings.getGoalMoney())
                .totalMoney(fundings.getTotalMoney())
                .startDate(fundings.getStartDate())
                .endDate(fundings.getEndDate())
                .fundingThumbnail(fundings.getFundingThumbnail())
                .fundingType(fundings.getFundingType())
                .build();
    }
}
