package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.Fundings;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingDetailViewDto {

    private String fundingTitle;

    public static FundingDetailViewDto toDto(Fundings fundings) {
        return FundingDetailViewDto.builder().build();
    }
}
