package com.accepted.givutake.gift.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftPurchaserDto {
    List<purchaser> purchasers;
}

