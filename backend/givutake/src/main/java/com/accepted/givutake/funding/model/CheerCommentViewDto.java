package com.accepted.givutake.funding.model;

import com.accepted.givutake.funding.entity.CheerComments;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CheerCommentViewDto {

    private int commentIdx;
    private String name;
    private String commentContent;
    private LocalDateTime createdDate;

    public static CheerCommentViewDto toDto(CheerComments cheerComments) {
        return CheerCommentViewDto.builder()
                .commentIdx(cheerComments.getCommentIdx())
                .name(cheerComments.getUsers().getName())
                .commentContent(cheerComments.getCommentContent())
                .createdDate(cheerComments.getCreatedDate())
                .build();
    }
}
