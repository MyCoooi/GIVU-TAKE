package com.project.givuandtake.core.data

import kotlinx.serialization.Serializable

data class CartItem(
    val name: String,
    val price: Int,
    val quantity: Int,
    val location: String
)

@Serializable
data class CartRequest(
    val giftIdx: Int,
    val amount: Int
)

@Serializable
data class CartResponse(
    val success: Boolean,
    val data: Any?,  // 성공 시 data는 null
    val code: String?,  // 실패 시 코드 (성공 시에는 null)
    val message: String?  // 실패 시 메시지 (성공 시에는 null)
)
