package com.accepted.givutake.user.corporation.model;

import com.accepted.givutake.user.common.entity.Users;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorporationViewDto {

    private String email;
    private String name;
    private String mobilePhone;
    private String landlinePhone;
    private String sido;
    private String sigungu;
    private String profileImageUrl;

    public static CorporationViewDto toDto(Users users) {
        return CorporationViewDto.builder()
                .email(users.getEmail())
                .name(users.getName())
                .mobilePhone(users.getMobilePhone())
                .landlinePhone(users.getLandlinePhone())
                .sido(users.getRegion().getSido())
                .sigungu(users.getRegion().getSigungu())
                .profileImageUrl(users.getProfileImageUrl())
                .build();
    }
}
