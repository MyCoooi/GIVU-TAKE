package com.accepted.givutake.user.client.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@ToString
@Table(name="Addresses")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Addresses extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_idx", nullable = false)
    private int addressIdx;

    @Column(name = "user_idx", nullable = false)
    private int userIdx;

    @Column(name = "region_idx", nullable = false)
    private int regionIdx;

    @Column(name = "address_name", nullable = false, length = 8)
    private String addressName;

    @Column(name = "zone_code", nullable = false, length = 5)
    private String zoneCode;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "user_selected_type", nullable = false)
    private char userSelectedType;

    @Column(name = "road_address", nullable = false, length = 50)
    private String roadAddress;

    @Column(name = "jibun_address", nullable = false, length = 50)
    private String jibunAddress;

    @Column(name = "detail_address", nullable = false, length = 50)
    private String detailAddress;

    @Column(name = "auto_road_address", nullable = false, length = 50)
    private String autoRoadAddress;

    @Column(name = "auto_jibun_address", nullable = false, length = 50)
    private String autoJibunAddress;

    @Column(name = "building_code", nullable = false, length = 25)
    private String buildingCode;

    @Column(name = "building_name", nullable = false, length = 20)
    private String buildingName;

    @Column(name = "is_apartment", nullable = false)
    private boolean isApartment;

    @Column(name = "sido", nullable = false, length = 10)
    private String sido;

    @Column(name = "sigungu", nullable = false, length = 10)
    private String sigungu;

    @Column(name = "sigungu_code", nullable = false, length = 5)
    private String sigunguCode;

    @Column(name = "road_name_code", nullable = false, length = 7)
    private String roadNameCode;

    @Column(name = "bcode", nullable = false, length = 10)
    private String bcode;

    @Column(name = "road_name", nullable = false, length = 10)
    private String roadName;

    @Column(name = "bname", nullable = false, length = 10)
    private String bname;

    @Column(name = "bname1", nullable = false, length = 10)
    private String bname1;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "is_reprentative", nullable = false)
    private boolean isRepresentative;

    // TODO: 추천 알고리즘에 사용
//    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
//    private BigDecimal latitude;

//    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
//    private BigDecimal longitude;
}
