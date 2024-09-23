package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.FundingReviews;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingReviewViewDto {

    private int reviewIdx;
    private String reviewContent;

    public static FundingReviewViewDto toDto(FundingReviews fundingReviews) {
        return FundingReviewViewDto.builder()
                .reviewIdx(fundingReviews.getReviewIdx())
                .reviewContent(fundingReviews.getReviewContent())
                .build();
    }
}
