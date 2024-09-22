package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Addresses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class AddressDto extends AddressAddDto {

    @NotNull(message = "주소 코드는 필수 입력 값 입니다.")
    private int addressIdx;

    @NotBlank(message = "사용자 코드는 필수 입력 값 입니다.")
    private int userIdx;

    public AddressDto toDto(Addresses addresses) {
        return AddressDto.builder()
                .addressIdx(addresses.getAddressIdx())
                .userIdx(addresses.getUserIdx())
                .regionIdx(addresses.getRegionIdx())
                .addressName(addresses.getAddressName())
                .zoneCode(addresses.getZoneCode())
                .address(addresses.getAddress())
                .userSelectedType(addresses.getUserSelectedType())
                .roadAddress(addresses.getRoadAddress())
                .jibunAddress(addresses.getJibunAddress())
                .detailAddress(addresses.getDetailAddress())
                .autoRoadAddress(addresses.getAutoRoadAddress())
                .autoJibunAddress(addresses.getAutoJibunAddress())
                .buildingCode(addresses.getBuildingCode())
                .buildingName(addresses.getBuildingName())
                .isApartment(addresses.isApartment())
                .sido(addresses.getSido())
                .sigungu(addresses.getSigungu())
                .sigunguCode(addresses.getSigunguCode())
                .roadNameCode(addresses.getRoadNameCode())
                .bcode(addresses.getBcode())
                .roadName(addresses.getRoadName())
                .bname(addresses.getBname())
                .bname1(addresses.getBname1())
                .isRepresentative(addresses.isRepresentative())
//                .latitude(addresses.getLatitude())
//                .longitude(addresses.getLongitude())
                .build();
    }
}
