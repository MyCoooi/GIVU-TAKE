package com.accepted.givutake.gift.model;

import com.accepted.givutake.gift.enumType.DeliveryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class UpdateOrderDto {

    @NotNull
    private DeliveryStatus status;

}
