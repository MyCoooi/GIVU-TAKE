package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Addresses;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressViewDto {

    protected int addressIdx;
    protected String addressName;
    protected String address;
    protected String detailAddress;
    protected boolean isRepresentative;

    public static AddressViewDto toDto(Addresses addresses) {
        return AddressViewDto.builder()
                .addressIdx(addresses.getAddressIdx())
                .addressName(addresses.getAddressName())
                .address(addresses.getAddress())
                .detailAddress(addresses.getDetailAddress())
                .isRepresentative(addresses.isRepresentative())
                .build();
    }
}
