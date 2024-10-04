package com.project.givuandtake.feature.gift

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.givuandtake.core.apis.RetrofitClient
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.data.GiftDetailData
import com.project.givuandtake.core.datastore.GiftRepository
import com.project.givuandtake.core.datastore.WishlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class GiftViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val giftRepository = GiftRepository(context)
    private val wishlistRepository = WishlistRepository

    // 모든 상품 목록
    open val allGiftDetails: StateFlow<List<GiftDetail>> = giftRepository.getAllGiftDetails()
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
            favoriteIds.contains(gift.giftIdx.toString())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())




    // 상품 상세 정보 관리 (MutableStateFlow로 관리)
    private val _giftDetail = MutableStateFlow<GiftDetailData?>(null)
    val giftDetail: StateFlow<GiftDetailData?> get() = _giftDetail.asStateFlow()
    
    // API에서 상품 데이터를 불러와 Room에 저장하는 메서드
    fun fetchGiftsFromApi(token: String) {
        viewModelScope.launch {
            try {
                // withContext로 명시적으로 IO 작업을 처리
                withContext(Dispatchers.IO) {
                    giftRepository.fetchGiftsFromApi(token)
                }
            } catch (e: Exception) {
                Log.e("GiftViewModel", "Error fetching gifts: ${e.message}", e)
            }
        }
    }

    // 상품 상세 정보 가져오기
    fun fetchGiftDetail(token: String, giftIdx: Int) {
        viewModelScope.launch {
            try {
                // withContext로 명시적으로 IO 작업을 처리
                val detail = withContext(Dispatchers.IO) {
                    giftRepository.fetchGiftDetailFromApi(token, giftIdx)
                }
                if (detail != null) {
                    _giftDetail.value = detail
                } else {
                    Log.e("GiftViewModel", "API 호출 실패 또는 데이터 없음")
                }
            } catch (e: Exception) {
                Log.e("GiftViewModel", "API 호출 오류: ${e.message}")
            }
        }
    }

    // 장바구니 아이템 개수 (기본 값 0)
    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> get() = _cartItemCount.asStateFlow()

    // 장바구니 아이템 개수 업데이트
    fun updateCartItemCount(newCount: Int) {
        _cartItemCount.value = newCount
    }
}
