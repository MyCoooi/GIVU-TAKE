package com.project.givuandtake.feature.gift

import androidx.compose.runtime.MutableState
import com.project.givuandtake.core.data.CartItem

fun addToCart(cartItems: MutableState<List<CartItem>>, newItem: CartItem) {
    cartItems.value = cartItems.value.toMutableList().apply {
        val existingItemIndex = indexOfFirst { it.name == newItem.name && it.location == newItem.location }

        if (existingItemIndex != -1) {
            // 기존 아이템이 있다면 수량을 업데이트
            val existingItem = this[existingItemIndex]
            this[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + newItem.quantity)
        } else {
            // 기존 아이템이 없다면 새 아이템 추가
            add(newItem)
        }
    }
}


