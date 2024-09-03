package com.accepted.givutake.user.jwt.model;

import com.accepted.givutake.user.common.model.AbstractUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetailsDto implements UserDetails {

    private final AbstractUserDto user;

    // 권한을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 빈 리스트 반환 (USER, ADMIN과 같은 권한 체계 없음)
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId().toString();
    }
}
