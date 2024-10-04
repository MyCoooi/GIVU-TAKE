package com.project.givuandtake.core.apis.Gift

import com.project.givuandtake.core.apis.RetrofitClient
import com.project.givuandtake.core.data.CartItemData

suspend fun fetchCartList(token: String): List<CartItemData>? {
    val response = RetrofitClient.giftApiService.getCartList("$token")
    if (response.isSuccessful) {
        val cartResponse = response.body()
        return cartResponse?.data
    } else {
        // 에러 처리
        println("Error: ${response.code()} - ${response.message()}")
        return null
    }
}
