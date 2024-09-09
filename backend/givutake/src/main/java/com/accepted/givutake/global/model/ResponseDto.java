package com.accepted.givutake.global.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ResponseDto {

    @Builder.Default
    private final boolean success = true;
    private Object data;
}
