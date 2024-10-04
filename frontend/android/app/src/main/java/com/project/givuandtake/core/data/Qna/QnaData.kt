package com.project.givuandtake.core.data.Qna

data class QnaData(
    val success: Boolean,
    val data: List<UserQna>
)

data class UserQna(
    val qnaIdx: Int,
    val userIdx: Int,
    val userName: String,
    val userProfileImage: String,
    val qnaTitle: String,
    val qnaContent: String
)
