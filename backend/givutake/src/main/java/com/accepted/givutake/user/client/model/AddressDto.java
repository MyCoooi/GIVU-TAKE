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

    @NotNull(message = "지역 코드는 필수 입력 값 입니다.")
    private Integer regionIdx;

    public AddressDto toDto(Addresses addresses) {
        return AddressDto.builder()
                .addressIdx(addresses.getAddressIdx())
                .userIdx(addresses.getUserIdx())
                .regionIdx(addresses.getRegionIdx())
                .addressName(addresses.getAddressName())
                .zoneCode(addresses.getZoneCode())
                .roadAddress(addresses.getRoadAddress())
                .jibunAddress(addresses.getJibunAddress())
                .detailAddress(addresses.getDetailAddress())
                .buildingName(addresses.getBuildingName())
                .isApartment(addresses.isApartment())
                .bname(addresses.getBname())
                .bname1(addresses.getBname1())
                .isRepresentative(addresses.isRepresentative())
//                .latitude(addresses.getLatitude())
//                .longitude(addresses.getLongitude())
                .build();
    }
}
