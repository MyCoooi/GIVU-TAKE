package com.accepted.givutake.gift.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GiftPercentageDto {

    private Map<String, List<StatDto>> statistics;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatDto {
        private String name;
        private Long count;
        private Double percentage;
    }

}
