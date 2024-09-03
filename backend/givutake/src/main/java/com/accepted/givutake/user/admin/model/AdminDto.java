package com.accepted.givutake.user.admin.model;

import com.accepted.givutake.user.client.model.ClientDto;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.model.AbstractUserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends AbstractUserDto {

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

    public AdminDto toDto(Users users) {
        return AdminDto.builder()
                .userIdx(users.getUserIdx())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .phone(users.getPhone())
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
