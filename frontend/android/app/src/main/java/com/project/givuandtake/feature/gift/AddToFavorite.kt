package com.project.givuandtake.feature.gift

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.project.givuandtake.core.data.Product
import com.project.givuandtake.core.datastore.FavoriteKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

suspend fun addToFavorites(dataStore: DataStore<Preferences>, product: Product) {
    dataStore.edit { preferences ->
        val favorites = preferences[FavoriteKeys.FAVORITES]?.toMutableSet() ?: mutableSetOf()

        if (favorites.contains(product.id.toString())) {
            // 이미 찜한 상품이면 제거
            favorites.remove(product.id.toString())
        } else {
            // 찜한 상품 추가
            favorites.add(product.id.toString())
        }

        preferences[FavoriteKeys.FAVORITES] = favorites
    }
}


fun getFavoriteProducts(dataStore: DataStore<Preferences>): Flow<Set<String>> {
    return dataStore.data.map { preferences ->
        preferences[FavoriteKeys.FAVORITES] ?: emptySet()
    }
}
