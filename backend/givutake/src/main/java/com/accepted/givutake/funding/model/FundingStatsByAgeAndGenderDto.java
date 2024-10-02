package com.accepted.givutake.funding.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingStatsByAgeAndGenderDto {
    private List<AgeGroupData> maleData;
    private List<AgeGroupData> femaleData;

    @AllArgsConstructor
    public static class AgeGroupData {
        private String ageGroup;
        private int totalFunding;
    }
}
