package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Addresses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotBlank(message = "")
    private String zoneCode;

    @NotBlank(message = "")
    private String address;

    @NotBlank(message = "")
    private char userSelectedType;

    @NotBlank(message = "")
    private String roadAddress;

    @NotBlank(message = "")
    private String jibunAddress;

    @NotBlank(message = "")
    private String detailAddress;

    @NotBlank(message = "")
    private String autoRoadAddress;

    @NotBlank(message = "")
    private String autoJibunAddress;

    @NotBlank(message = "")
    private String buildingCode;

    @NotBlank(message = "")
    private String buildingName;

    @NotBlank(message = "")
    private boolean isApartment;

    @NotBlank(message = "")
    private String sido;

    @NotBlank(message = "")
    private String sigungu;

    @NotBlank(message = "")
    private String sigunguCode;

    @NotBlank(message = "")
    private String roadNameCode;

    @NotBlank(message = "")
    private String bcode;

    @NotBlank(message = "")
    private String roadName;

    @NotBlank(message = "")
    private String bname;

    @NotBlank(message = "")
    private String bname1;

    // TODO: 추천 알고리즘에 사용
//    private BigDecimal latitude;
//    private BigDecimal longitude;

    public Addresses toEntity(int userIdx) {
        return Addresses.builder()
                .addressIdx(0)
                .userIdx(userIdx)
                .zoneCode(this.zoneCode)
                .address(this.address)
                .userSelectedType(this.userSelectedType)
                .roadAddress(this.roadAddress)
                .jibunAddress(this.jibunAddress)
                .detailAddress(this.detailAddress)
                .autoRoadAddress(this.autoRoadAddress)
                .autoJibunAddress(this.autoJibunAddress)
                .buildingCode(this.buildingCode)
                .buildingName(this.buildingName)
                .isApartment(this.isApartment)
                .sido(this.sido)
                .sigungu(this.sigungu)
                .sigunguCode(this.sigunguCode)
                .roadNameCode(this.roadNameCode)
                .bcode(this.bcode)
                .roadName(this.roadName)
                .bname(this.bname)
                .bname1(this.bname1)
//                .latitude(this.latitude)
//                .longitude(this.longitude)
                .build();
    }
}
