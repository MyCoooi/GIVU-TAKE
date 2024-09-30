package com.project.givuandtake.feature.mypage.MyDonation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.datastore.GiftRepository
import com.project.givuandtake.core.datastore.WishlistRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class WishlistViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    // 실제 데이터 소스 (예: Repository)를 주입받아야 합니다.
    private val giftRepository = GiftRepository(context)

    // wishlistItems 타입을 명시적으로 지정 (StateFlow<Set<String>>)
    val wishlistItemsIds: StateFlow<Set<String>> = WishlistRepository.getWishlist(context)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet<String>()) // 타입 지정

    // 아이템을 위시리스트에 추가
    fun addItemToWishlist(giftDetail: GiftDetail) {
        viewModelScope.launch {
            WishlistRepository.addItemToWishlist(context, giftDetail.giftIdx.toString())
        }
    }

    // 아이템을 위시리스트에서 제거
    fun removeItemFromWishlist(giftDetail: GiftDetail) {
        viewModelScope.launch {
            WishlistRepository.removeItemFromWishlist(context, giftDetail.giftIdx.toString())
        }
    }

    // 찜 상태를 토글 (추가/제거)
    fun toggleWishlistItem(giftDetail: GiftDetail) {
        viewModelScope.launch {
            val itemId = giftDetail.giftIdx.toString() // GiftDetail의 id를 String으로 변환
            val currentWishlist = wishlistItemsIds.value // 현재 위시리스트 Set 가져오기
            Log.d("WishlistViewModel", "currentWishlist: $currentWishlist")

            // contains 사용하여 해당 아이템이 있는지 확인
            if (currentWishlist.contains(itemId)) {
                removeItemFromWishlist(giftDetail)
            } else {
                addItemToWishlist(giftDetail)
            }

            // 로그로 상태 변화 확인
            val updatedWishlist = wishlistItemsIds.value
            Log.d("WishlistViewModel", "Updated wishlist: $updatedWishlist")
        }
    }
}
