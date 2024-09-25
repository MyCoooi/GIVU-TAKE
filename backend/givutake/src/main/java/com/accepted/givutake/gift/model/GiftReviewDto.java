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
    private String reviewContent;
    private Integer giftIdx;
    private String giftName;
    private String giftThumbnail;
    private Integer userIdx;
    private String userName;
    private String userProfileImage;
    private Integer orderIdx;
    private Integer likedCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
