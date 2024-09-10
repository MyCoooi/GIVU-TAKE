package com.accepted.givutake.gift.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GiftDto {
    private Integer giftIdx;
    private String giftName;
    private Integer corporationIdx;
    private String corporationName;
    private Integer cartegoryIdx;
    private String categoryName;
    private String giftThumbnail;
    private String giftContent;
    private Integer price;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

