package com.accepted.givutake.user.common.model;

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
public class AbstractUserDto {

    protected int userIdx;
    protected String name;
    protected String email;
    protected String password;
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

}
