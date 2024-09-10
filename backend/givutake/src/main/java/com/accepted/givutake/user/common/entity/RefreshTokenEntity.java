package com.accepted.givutake.user.common.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("RefreshToken")
@Getter
@Setter
@ToString
@Builder
public class RefreshTokenEntity {
    @Id
    private String email;
    private String refreshToken;
    
    // @TimeToLive: 자동 삭제 시간
    @TimeToLive
    private Long ttl;
}
