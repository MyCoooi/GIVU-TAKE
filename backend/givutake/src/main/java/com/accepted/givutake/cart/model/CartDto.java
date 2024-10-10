package com.accepted.givutake.cart.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CartDto {
    private Integer cartIdx;
    private Integer giftIdx;
    private String giftName;
    private String giftThumbnail;
    private String corporationName;
    private Integer userIdx;
    private Integer amount;
    private Integer price;
}
