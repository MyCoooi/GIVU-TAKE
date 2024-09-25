package com.project.givuandtake.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.project.givuandtake.core.data.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore 초기화
val Context.cartDataStore by preferencesDataStore("cart")

object CartKeys {
    val CART_ITEMS = stringSetPreferencesKey("cart_items")
}

// CartItem을 문자열로 변환하는 함수
private fun CartItem.toStorageString(): String {
    return "${this.name}|${this.price}|${this.quantity}|${this.location}"
}

// 문자열을 CartItem으로 변환하는 함수
private fun String.toCartItem(): CartItem {
    val parts = this.split("|")
    return CartItem(
        name = parts[0],
        price = parts[1].toInt(),
        quantity = parts[2].toInt(),
        location = parts[3]
    )
}

// 장바구니 항목 저장
suspend fun saveCartItems(context: Context, cartItems: List<CartItem>) {
    val cartStrings = cartItems.map { it.toStorageString() }.toSet() // List<CartItem>을 Set<String>으로 변환
    context.cartDataStore.edit { preferences ->
        preferences[CartKeys.CART_ITEMS] = cartStrings
    }
}

// 장바구니 항목 불러오기
fun getCartItems(context: Context): Flow<List<CartItem>> {
    return context.cartDataStore.data.map { preferences ->
        val cartStrings = preferences[CartKeys.CART_ITEMS] ?: emptySet()
        cartStrings.map { it.toCartItem() } // Set<String>을 List<CartItem>으로 변환
    }
}