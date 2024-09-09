package com.accepted.givutake.global.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {

    @Builder.Default
    private final boolean success = true;
    private Object data;
}