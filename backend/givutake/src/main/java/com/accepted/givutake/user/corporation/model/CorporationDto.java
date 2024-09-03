package com.accepted.givutake.user.corporation.model;

import com.accepted.givutake.user.common.model.AbstractUserDto;
import com.accepted.givutake.user.common.entity.Users;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorporationDto extends AbstractUserDto {

    private Integer regionIdx;

    public Users toEntity() {
        return Users.builder()
                .userIdx(this.userIdx)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .phone(this.phone)
                .isMale(null)
                .birth(null)
                .addressIdx(null)
                .regionIdx(null)
                .cardIdx(null)
                .profileImageUrl(this.profileImageUrl)
                .roles(this.roles)
                .isSocial(this.isSocial)
                .socialType(this.socialType)
                .socialSerialNum(this.socialSerialNum)
                .status(this.status)
                .isWithdraw(this.isWithdraw)
                .build();
    }

    public CorporationDto toDto(Users users) {
        return CorporationDto.builder()
                .userIdx(users.getUserIdx())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .phone(users.getPhone())
                .regionIdx(users.getRegionIdx())
                .profileImageUrl(users.getProfileImageUrl())
                .roles(users.getRoles())
                .isSocial(users.isSocial())
                .socialType(users.getSocialType())
                .socialSerialNum(users.getSocialSerialNum())
                .status(users.getStatus())
                .isWithdraw(users.isWithdraw())
                .build();
    }
}
