package com.accepted.givutake.wish.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class WishDto {

    @NotNull
    private int giftIdx;
}
