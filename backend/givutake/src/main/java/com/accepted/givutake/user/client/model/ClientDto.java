package com.accepted.givutake.user.client.model;

import com.accepted.givutake.user.common.model.AbstractUserDto;
import com.accepted.givutake.user.common.entity.Users;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends AbstractUserDto {

    private Boolean isMale;
    private LocalDateTime birth;
    private Integer addressIdx;
    private Integer cardIdx;

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
                .regionIdx(null)
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

    public ClientDto toDto(Users users) {
        return ClientDto.builder()
                .userIdx(users.getUserIdx())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .phone(users.getPhone())
                .isMale(users.getIsMale())
                .birth(users.getBirth())
                .addressIdx(users.getAddressIdx())
                .cardIdx(users.getCardIdx())
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
