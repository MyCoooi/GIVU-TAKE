package com.accepted.givutake.gift.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GiftReviewDto {
    private Integer reviewIdx;
    private String reviewTitle;
    private String reviewContent;
    private Integer giftIdx;
    private Integer userIdx;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
