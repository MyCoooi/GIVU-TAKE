package com.accepted.givutake.user.common.model;

import com.accepted.givutake.region.entity.Region;
import com.accepted.givutake.user.client.model.ClientViewDto;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import com.accepted.givutake.user.corporation.model.CorporationViewDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private int userIdx;
    private String name;
    private String email;
    private String password;
    private String mobilePhone;
    private String landlinePhone;
    private Boolean isMale;
    private LocalDate birth;
    private Region region;
    private Integer cardIdx;
    private String profileImageUrl;
    private Roles roles;
    private boolean isSocial;
    private SocialType socialType;
    private String socialSerialNum;
    private Byte status;
    private boolean isWithdraw;

    public static UserDto toDto(Users user) {
        return UserDto.builder()
                .userIdx(user.getUserIdx())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .mobilePhone(user.getMobilePhone())
                .landlinePhone(user.getLandlinePhone())
                .isMale(user.getIsMale())
                .birth(user.getBirth())
                .region(user.getRegion())
                .cardIdx(user.getCardIdx())
                .profileImageUrl(user.getProfileImageUrl())
                .roles(user.getRoles())
                .isSocial(user.isSocial())
                .socialType(user.getSocialType())
                .socialSerialNum(user.getSocialSerialNum())
                .status(user.getStatus())
                .isWithdraw(user.isWithdraw())
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .userIdx(this.userIdx)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .mobilePhone(this.mobilePhone)
                .landlinePhone(this.landlinePhone)
                .isMale(this.isMale)
                .birth(this.birth)
                .region(this.region)
                .cardIdx(this.cardIdx)
                .profileImageUrl(this.profileImageUrl)
                .roles(this.roles)
                .isSocial(this.isSocial)
                .socialType(this.socialType)
                .socialSerialNum(this.socialSerialNum)
                .status(this.status)
                .isWithdraw(this.isWithdraw)
                .build();
    }

    public ClientViewDto toClientViewDto() {
        return ClientViewDto.builder()
                .email(this.email)
                .name(this.name)
                .mobilePhone(this.mobilePhone)
                .landlinePhone(this.landlinePhone)
                .isMale(this.isMale)
                .birth(this.birth)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }

    public CorporationViewDto toCorporationViewDto() {
        return CorporationViewDto.builder()
                .email(this.email)
                .name(this.name)
                .mobilePhone(this.mobilePhone)
                .landlinePhone(this.landlinePhone)
                .sido(this.region.getSido())
                .sigungu(this.region.getSigungu())
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
