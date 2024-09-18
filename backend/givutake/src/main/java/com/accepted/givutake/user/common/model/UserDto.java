package com.accepted.givutake.user.common.model;

import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime birth;
    private Integer addressIdx;
    private Integer regionIdx;
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
                .addressIdx(user.getAddressIdx())
                .regionIdx(user.getRegionIdx())
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
                .addressIdx(this.addressIdx)
                .regionIdx(this.regionIdx)
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

    public ResponseUserDto toResponseUserDto() {
        return ResponseUserDto.builder()
                .name(this.name)
                .mobilePhone(this.mobilePhone)
                .landlinePhone(this.landlinePhone)
                .isMale(this.isMale)
                .birth(this.birth)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
