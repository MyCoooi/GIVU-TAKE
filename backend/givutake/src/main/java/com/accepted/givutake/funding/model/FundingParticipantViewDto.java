package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.FundingParticipants;
import com.accepted.givutake.funding.entity.Fundings;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingParticipantViewDto {

    private int fundingIdx;
    private String fundingThumbnail;
    private String fundingTitle;
    private int fundingFee;
    private char fundingType;
    private LocalDateTime createdDate;

    public static FundingParticipantViewDto toDto(FundingParticipants fundingParticipants) {
        Fundings fundings = fundingParticipants.getFundings();
        return FundingParticipantViewDto.builder()
                .fundingIdx(fundings.getFundingIdx())
                .fundingThumbnail(fundings.getFundingThumbnail())
                .fundingTitle(fundings.getFundingTitle())
                .fundingFee(fundingParticipants.getFundingFee())
                .fundingType(fundings.getFundingType())
                .createdDate(fundingParticipants.getCreatedDate())
                .build();
    }
}
