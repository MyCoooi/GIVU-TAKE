package com.project.givuandtake.feature.gift

import android.content.Context
import com.project.givuandtake.core.data.CartItem
import com.project.givuandtake.core.datastore.getCartItems
import com.project.givuandtake.core.datastore.saveCartItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

import com.project.givuandtake.core.data.GiftDetail

// DataStore에서 cartItems를 관리하는 함수 (GiftDetail을 추가할 수 있도록 수정)
suspend fun addToCart(context: Context, giftDetail: GiftDetail, quantity: Int) {
    // 현재 장바구니 항목을 DataStore에서 불러옴
    val currentCartItems = withContext(Dispatchers.IO) {
        getCartItems(context).first()
    }

    // GiftDetail을 CartItem으로 변환
    val newItem = CartItem(
        name = giftDetail.name,
        price = giftDetail.price,
        quantity = quantity,
        location = giftDetail.location
    )

    // 장바구니 업데이트 로직
    val updatedCartItems = currentCartItems.toMutableList().apply {
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

    // DataStore에 업데이트된 장바구니 저장
    saveCartItems(context, updatedCartItems)
}




