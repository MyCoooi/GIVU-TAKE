package com.project.givuandtake.feature.gift

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.datastore.GiftRepository
import com.project.givuandtake.core.datastore.WishlistRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GiftViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val giftRepository = GiftRepository(context)
    private val wishlistRepository = WishlistRepository // 이전에 구현한 WishlistRepository

    // 모든 상품 목록
    val allGiftDetails: StateFlow<List<GiftDetail>> = giftRepository.getAllGiftDetails()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 찜한 상품 ID 목록
    val wishlistItemsIds: StateFlow<Set<String>> = wishlistRepository.getWishlist(context)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // 찜한 상품 목록
    val wishlistItems: StateFlow<List<GiftDetail>> = combine(
        allGiftDetails,
        wishlistItemsIds
    ) { giftDetails, favoriteIds ->
        giftDetails.filter { gift ->
            favoriteIds.contains(gift.id.toString())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 초기 데이터 삽입 (필요 시 호출)
    fun insertInitialGiftDetails() {
        viewModelScope.launch {
            val initialGiftDetails = listOf(
                GiftDetail(1, "상품 1", 10000, "url1", "강원도 평창"),
                GiftDetail(2, "상품 2", 20000, "url2", "부산"),
                GiftDetail(3, "상품 3", 30000, "url3", "대구"),
                GiftDetail(4, "상품 4", 40000, "url4", "광주"),
                GiftDetail(5, "상품 5", 50000, "url5", "인천"),
                GiftDetail(6, "상품 6", 60000, "url6", "울산")
            )
            giftRepository.insertGiftDetails(initialGiftDetails)
        }
    }

    // 장바구니 아이템 개수 (예시로 임의의 값을 사용)
    val cartItemCount: StateFlow<Int> = MutableStateFlow(0)
}
