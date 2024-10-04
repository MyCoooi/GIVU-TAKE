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

    // Map<statistics type, Map<name, StatDto>>
    private Map<String, Map<String, StatDto>> statistics;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatDto {
        private Long count;
        private Double percentage;
    }

}

//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class GiftPercentageDto {
//
//    // Map<type, Map<name, StatDto>>
//    private Map<String, List<StatDto>> statistics;
//
//    @Getter
//    @Setter
//    @Builder
//    @ToString
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class StatDto {
//        private String name;
//        private Long count;
//        private Double percentage;
//    }
//
//}
