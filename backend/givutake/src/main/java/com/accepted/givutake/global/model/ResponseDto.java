package com.accepted.givutake.global.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ResponseDto {

    @Builder.Default
    private final boolean isSuccess = true;
    private Object data;
}
