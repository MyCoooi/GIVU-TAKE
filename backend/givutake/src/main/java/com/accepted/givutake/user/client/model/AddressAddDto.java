package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.client.entity.Addresses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressAddDto {

    @NotNull(message = "지역 코드는 필수 입력 값 입니다.")
    protected Integer regionIdx;

    @NotBlank(message = "배송지명은 필수 입력 값 입니다.")
    @Size(max = 8, message = "배송지명은 최대 8자 이하여야 합니다.")
    protected String addressName;

    @NotBlank(message = "국가기초구역번호는 필수 입력 값 입니다.")
    @Size(max = 5, message = "국가기초구역번호는 최대 5자 이하여야 합니다.")
    protected String zoneCode;

    @NotBlank(message = "기본주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "기본주소는 최대 50자 이하여야 합니다.")
    protected String address;

    @NotNull(message = "주소 타입은 필수 입력 값 입니다.")
    protected Character userSelectedType;

    @NotBlank(message = "도로명 주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "도로명 주소는 최대 50자 이하여야 합니다.")
    protected String roadAddress;

    @NotBlank(message = "지번 주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "지번 주소 최대 50자 이하여야 합니다.")
    protected String jibunAddress;

    @NotBlank(message = "상세 주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "상세 주소는 최대 50자 이하여야 합니다.")
    protected String detailAddress;

    @NotNull(message = "임의 도로명 주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "임의 도로명 주소는 최대 50자 이하여야 합니다.")
    protected String autoRoadAddress;

    @NotNull(message = "임의 지번 주소는 필수 입력 값 입니다.")
    @Size(max = 50, message = "임의 지번 주소는 최대 50자 이하여야 합니다.")
    protected String autoJibunAddress;

    @NotNull(message = "건물관리번호는 필수 입력 값 입니다.")
    @Size(max = 25, message = "건물관리번호는 최대 25자 이하여야 합니다.")
    protected String buildingCode;

    @NotNull(message = "건물명은 필수 입력 값 입니다.")
    @Size(max = 20, message = "건물명은 최대 20자 이하여야 합니다.")
    protected String buildingName;

    @NotNull(message = "공통주택 여부는 필수 입력 값 입니다.")
    protected Boolean isApartment;

    @NotBlank(message = "도/시 이름은 필수 입력 값 입니다.")
    @Size(max = 10, message = "도/시 이름은 최대 10자 이하여야 합니다.")
    protected String sido;

    @NotBlank(message = "시/군/구 이름은 필수 입력 값 입니다.")
    @Size(max = 10, message = "시/군/구 이름은 최대 10자 이하여야 합니다.")
    protected String sigungu;

    @NotBlank(message = "시/군/구 코드는 필수 입력 값 입니다.")
    @Size(max = 5, message = "배시/군/구 코드는 최대 5자 이하여야 합니다.")
    protected String sigunguCode;

    @NotBlank(message = "도로명 코드는 필수 입력 값 입니다.")
    @Size(max = 7, message = "도로명 코드는 최대 7자 이하여야 합니다.")
    protected String roadNameCode;

    @NotBlank(message = "법정동/법정리 코드는 필수 입력 값 입니다.")
    @Size(max = 10, message = "법정동/법정리 코드는 최대 10자 이하여야 합니다.")
    protected String bcode;

    @NotBlank(message = "도로명은 필수 입력 값 입니다.")
    @Size(max = 10, message = "도로명은 최대 10자 이하여야 합니다.")
    protected String roadName;

    @NotNull(message = "법정동/법정리 이름은 필수 입력 값 입니다.")
    @Size(max = 10, message = "법정동/법정리 이름은 최대 10자 이하여야 합니다.")
    protected String bname;

    @NotNull(message = "법정리의 읍/면 이름은 필수 입력 값 입니다.")
    @Size(max = 10, message = "법정리의 읍/면 이름은 최대 10자 이하여야 합니다.")
    protected String bname1;

    @NotNull(message = "대표 주소 여부는 필수 입력 값 입니다.")
    protected Boolean isRepresentative;

    // TODO: 추천 알고리즘에 사용
//    private BigDecimal latitude;
//    private BigDecimal longitude;

    public Addresses toEntity(int userIdx) {
        return Addresses.builder()
                .userIdx(userIdx)
                .regionIdx(this.regionIdx)
                .addressName(this.addressName)
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
                .isRepresentative(this.isRepresentative)
//                .latitude(this.latitude)
//                .longitude(this.longitude)
                .build();
    }
}
