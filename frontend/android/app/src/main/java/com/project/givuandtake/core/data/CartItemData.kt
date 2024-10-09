package com.project.givuandtake.core.data

import kotlinx.serialization.Serializable

@Serializable
data class CartData(
    val cartIdx: Int,   // 장바구니 항목 ID
    val giftIdx: Int,   // giftIdx 추가
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

@Serializable
data class CartItemDataResponse(
    val success: Boolean,
    val data: List<CartItemData>
)

@Serializable
data class CartItemData(
    val cartIdx: Int,
    val giftIdx: Int,
    val giftName: String,
    val giftThumbnail: String?,
    val userIdx: Int,
    val amount: Int,
    val price: Int,
    var location: String? = null  // 초기화 후 값을 설정
) {
    init {
        location = extractLocationFromGiftName(giftName)  // giftName을 기반으로 location 값 설정
    }
}

// 대괄호 [] 안의 내용을 추출하는 함수
fun extractLocationFromGiftName(giftName: String): String {
    val regex = "\\[(.*?)\\]".toRegex()  // 대괄호 안의 텍스트를 추출하는 정규식
    val matchResult = regex.find(giftName)
    return matchResult?.groupValues?.get(1) ?: "Unknown"  // 매칭된 값이 있으면 반환, 없으면 "Unknown"
}


