package com.project.givuandtake.core.data.Card

import com.project.givuandtake.core.data.Address.UserAddress

data class CardData(
    val success: Boolean,
    val data: List<UserCard>
)

data class UserCard(
    val cardCompany: String,
    val cardNumber: String,
    val cardCVC: String,
    val cardExpiredDate: String,
    val cardPassword: String,
    val isRepresentative: Boolean
)