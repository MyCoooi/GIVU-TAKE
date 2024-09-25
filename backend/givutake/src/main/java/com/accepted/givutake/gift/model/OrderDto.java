package com.accepted.givutake.gift.model;

import com.accepted.givutake.gift.enumType.DeliveryStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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
    private String giftThumbnail;
    private String paymentMethod;
    private Integer amount;
    private Integer price;
    private DeliveryStatus status;
    private LocalDateTime createdDate;
}
