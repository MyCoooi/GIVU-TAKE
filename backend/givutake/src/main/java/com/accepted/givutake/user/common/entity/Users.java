package com.accepted.givutake.user.common.entity;

import com.accepted.givutake.global.entity.BaseTimeEntity;
import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="Users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", nullable = false)
    private int userIdx;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    // 사용자만 해당
    @Column(name = "is_male", nullable = true)
    private Boolean isMale;

    // 사용자만 해당
    @Column(name = "birth", nullable = true)
    private LocalDateTime birth;

    // 사용자만 해당
    @Column(name = "address_idx", nullable = true)
    private Integer addressIdx;

    // 수혜자만 해당
    @Column(name = "region_idx", nullable = true)
    private Integer regionIdx;

    // 사용자만 해당
    @Column(name = "card_idx", nullable = true)
    private Integer cardIdx;

    @Column(name = "profile_image_url", nullable = true)
    private String profileImageUrl;

    @Column(name = "roles", nullable = false)
    private Roles roles;

    @Column(name = "is_social", nullable = false)
    private boolean isSocial;

    @Column(name = "social_type", nullable = true)
    private SocialType socialType;

    @Column(name = "social_serial_num", nullable = true)
    private String socialSerialNum;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "is_withdraw", nullable = false)
    private boolean isWithdraw;

}
