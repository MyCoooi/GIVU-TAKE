package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.FundingReviews;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingReviewViewDto {

    private String reviewContent;

    public static FundingReviewViewDto toDto(FundingReviews fundingReviews) {
        return FundingReviewViewDto.builder()
                .reviewContent(fundingReviews.getReviewContent())
                .build();
    }
}
