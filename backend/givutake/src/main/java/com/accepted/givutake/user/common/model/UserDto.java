package com.accepted.givutake.user.common.model;

import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    protected int userIdx;
    protected String name;
    protected String email;
    protected String password;
    protected Boolean isMale; // 사용자만 입력
    protected LocalDateTime birth; // 사용자만 입력
    protected Integer addressIdx; // 사용자만 입력
    protected Integer cardIdx; // 사용자만 입력
    protected Integer regionIdx; // 수혜자만 입력
    protected String phone;
    protected String profileImageUrl;
    protected Roles roles;
    protected boolean isSocial;
    protected SocialType socialType;
    protected String socialSerialNum;
    protected LocalDateTime createDate;
    protected LocalDateTime modifiedDate;
    protected int status;
    protected boolean isWithdraw;

    public Users toEntity() {
        return Users.builder()
                .userIdx(this.userIdx)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .phone(this.phone)
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

    public static UserDto toDto(Users users) {
        return UserDto.builder()
                .userIdx(users.getUserIdx())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .isMale(users.getIsMale())
                .birth(users.getBirth())
                .addressIdx(users.getAddressIdx())
                .regionIdx(users.getRegionIdx())
                .cardIdx(users.getCardIdx())
                .phone(users.getPhone())
                .profileImageUrl(users.getProfileImageUrl())
                .roles(users.getRoles())
                .isSocial(users.isSocial())
                .socialType(users.getSocialType())
                .socialSerialNum(users.getSocialSerialNum())
                .status(users.getStatus())
                .createDate(users.getCreatedDate())
                .modifiedDate(users.getModifiedDate())
                .isWithdraw(users.isWithdraw())
                .build();
    }

}