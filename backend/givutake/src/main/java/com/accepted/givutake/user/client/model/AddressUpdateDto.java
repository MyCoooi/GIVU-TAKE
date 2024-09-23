package com.accepted.givutake.user.client.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDto extends AddressAddDto {

    @NotNull(message = "주소 코드는 필수 입력 값 입니다.")
    private int addressIdx;

}
