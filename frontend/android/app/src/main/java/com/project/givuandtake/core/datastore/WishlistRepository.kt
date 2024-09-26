package com.project.givuandtake.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.project.givuandtake.core.data.WishlistItem
import com.project.givuandtake.core.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object WishlistRepository {
    private val WISHLIST_KEY = stringSetPreferencesKey("wishlist_items")

    fun getWishlist(context: Context): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[WISHLIST_KEY] ?: emptySet()
        }
    }

    suspend fun addItemToWishlist(context: Context, itemId: String) {
        context.dataStore.edit { preferences ->
            val currentWishlist = preferences[WISHLIST_KEY]?.toMutableSet() ?: mutableSetOf()
            currentWishlist.add(itemId)
            preferences[WISHLIST_KEY] = currentWishlist
        }
    }

    suspend fun removeItemFromWishlist(context: Context, itemId: String) {
        context.dataStore.edit { preferences ->
            val currentWishlist = preferences[WISHLIST_KEY]?.toMutableSet() ?: mutableSetOf()
            currentWishlist.remove(itemId)
            preferences[WISHLIST_KEY] = currentWishlist
        }
    }
}

