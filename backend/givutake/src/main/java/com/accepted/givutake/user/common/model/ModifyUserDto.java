package com.accepted.givutake.user.common.model;

import com.accepted.givutake.user.common.enumType.Roles;
import com.accepted.givutake.user.common.enumType.SocialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ModifyUserDto {
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    private Boolean isMale; // 사용자만 입력

    private LocalDateTime birth; // 사용자만 입력

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String mobilePhone;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "일반 전화 번호 형식이 올바르지 않습니다.")
    private String landlinePhone;

    @Pattern(regexp = "^(http|https)://[\\w.-]+(:\\d+)?(/\\S*)?$", message = "URL 형식이 올바르지 않습니다.")
    private String profileImageUrl;
}
