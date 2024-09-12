package com.accepted.givutake.qna.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AnswerDto {
    private Integer answerIdx;
    private Integer userIdx;
    private String userName;
    private String userProfileImage;
    private Integer qnaIdx;
    private String answerContent;
}
