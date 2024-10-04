package com.project.givuandtake.core.data.Card

import com.project.givuandtake.core.data.Address.UserAddress

data class CardData(
    val success: Boolean,
    val data: List<UserCard>
)

data class UserCard(
    val cardIdx: Int,  // 추가
    val cardCompany: String,
    val cardNumber: String,
    val cardCVC: String,
    val cardExpiredDate: String,
    val representative: Boolean  // 수정
)