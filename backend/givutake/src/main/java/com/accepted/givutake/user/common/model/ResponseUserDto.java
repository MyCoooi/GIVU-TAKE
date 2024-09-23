package com.accepted.givutake.user.common.model;

import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ResponseUserDto {

    private String email;
    private String name;
    private String mobilePhone;
    private String landlinePhone;
    private Boolean isMale;
    private LocalDateTime birth;
    private String profileImageUrl;

    public static ResponseUserDto toDto(Users users) {
        return ResponseUserDto.builder()
                .email(users.getEmail())
                .name(users.getName())
                .mobilePhone(users.getMobilePhone())
                .landlinePhone(users.getLandlinePhone())
                .isMale(users.getIsMale())
                .birth(users.getBirth())
                .profileImageUrl(users.getProfileImageUrl())
                .build();
    }
}
