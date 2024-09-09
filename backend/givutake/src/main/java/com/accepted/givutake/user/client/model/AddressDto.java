package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Addresses;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotNull(message = "국가기초구역번호는 필수 입력 값 입니다.")
    private String zoneCode;

    @NotNull(message = "기본주소는 필수 입력 값 입니다.")
    private String address;

    @NotNull(message = "주소 타입은 필수 입력 값 입니다.")
    private char userSelectedType;

    @NotNull(message = "도로명 주소는 필수 입력 값 입니다.")
    private String roadAddress;

    @NotNull(message = "지번 주소는 필수 입력 값 입니다.")
    private String jibunAddress;

    @NotNull(message = "상세 주소는 필수 입력 값 입니다.")
    private String detailAddress;

    @NotNull(message = "임의 도로명 주소는 필수 입력 값 입니다.")
    private String autoRoadAddress;

    @NotNull(message = "임의 지번 주소는 필수 입력 값 입니다.")
    private String autoJibunAddress;

    @NotNull(message = "건물관리번호는 필수 입력 값 입니다.")
    private String buildingCode;

    @NotNull(message = "건물명은 필수 입력 값 입니다.")
    private String buildingName;

    @NotNull(message = "공통주택 여부는 필수 입력 값 입니다.")
    private boolean isApartment;

    @NotNull(message = "도/시 이름은 필수 입력 값 입니다.")
    private String sido;

    @NotNull(message = "시/군/구 이름은 필수 입력 값 입니다.")
    private String sigungu;

    @NotNull(message = "시/군/구 코드는 필수 입력 값 입니다.")
    private String sigunguCode;

    @NotNull(message = "도로명 코드는 필수 입력 값 입니다.")
    private String roadNameCode;

    @NotNull(message = "법정동/법정리 코드는 필수 입력 값 입니다.")
    private String bcode;

    @NotNull(message = "도로명은 필수 입력 값 입니다.")
    private String roadName;

    @NotNull(message = "법정도/법정리 이름은 필수 입력 값 입니다.")
    private String bname;

    @NotNull(message = "법정리의 읍/면 이름은 필수 입력 값 입니다.")
    private String bname1;

    // TODO: 추천 알고리즘에 사용
//    private BigDecimal latitude;
//    private BigDecimal longitude;

    public Addresses toEntity(int userIdx) {
        return Addresses.builder()
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
