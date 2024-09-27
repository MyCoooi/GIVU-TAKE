package com.accepted.givutake.pdf;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationParticipantsDto {

    private String type;
    private String name;
    private LocalDate date;
    private int price;
    private String ref;
}
