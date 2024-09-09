package com.accepted.givutake.global.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ExceptionDto {

    @Builder.Default
    private final boolean success = false;
    private String code;
    private String message;

}