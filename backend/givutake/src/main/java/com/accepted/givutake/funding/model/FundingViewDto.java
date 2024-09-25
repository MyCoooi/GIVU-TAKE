package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.Fundings;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingViewDto {

    private String fundingTitle;

    public static FundingViewDto toDto(Fundings fundings) {
        return FundingViewDto.builder().build();
    }
}
