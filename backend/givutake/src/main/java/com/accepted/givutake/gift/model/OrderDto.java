package com.accepted.givutake.gift.model;

import com.accepted.givutake.gift.enumType.DeliveryStatus;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OrderDto {
    private Integer orderIdx;
    private Integer userIdx;
    private Integer giftIdx;
    private String giftName;
    private String paymentMethod;
    private Integer amount;
    private Integer price;
    private DeliveryStatus status;
}
