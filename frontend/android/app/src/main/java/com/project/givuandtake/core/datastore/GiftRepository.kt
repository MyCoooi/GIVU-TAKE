package com.project.givuandtake.core.datastore

import android.content.Context
import com.project.givuandtake.core.apis.RetrofitClient
import com.project.givuandtake.core.data.DatabaseProvider
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.data.GiftResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GiftRepository(private val context: Context) {
    private val giftDetailDao = DatabaseProvider.getDatabase(context).giftDetailDao()

    // Room에서 모든 상품 데이터를 가져옴
    fun getAllGiftDetails(): Flow<List<GiftDetail>> {
        return giftDetailDao.getAllGiftDetails()
    }

    // Room에서 특정 상품 ID로 데이터를 가져옴
    fun getGiftDetailsByIds(ids: List<Int>): Flow<List<GiftDetail>> {
        return giftDetailDao.getGiftDetailsByIds(ids)
    }

    // API에서 데이터를 불러와 Room에 저장
    suspend fun fetchGiftsFromApi(token: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.giftApiService.getGifts(token).execute()

                if (response.isSuccessful) {
                    response.body()?.let { giftResponse: GiftResponse ->
                        if (giftResponse.success) {
                            // API 데이터를 GiftDetail 엔티티에 매핑
                            val giftDetails = giftResponse.data.map { giftItem ->
                                GiftDetail(
                                    giftIdx = giftItem.giftIdx,
                                    giftName = giftItem.giftName,
                                    corporationIdx = giftItem.corporationIdx,
                                    corporationName = giftItem.corporationName,
                                    corporationSido = giftItem.corporationSido,
                                    corporationSigungu = giftItem.corporationSigungu,
                                    categoryIdx = giftItem.categoryIdx,
                                    categoryName = giftItem.categoryName,
                                    giftThumbnail = giftItem.giftThumbnail,
                                    giftContent = giftItem.giftContent,
                                    price = giftItem.price,
                                    createdDate = giftItem.createdDate,
                                    modifiedDate = giftItem.modifiedDate
                                )
                            }
                            // Room에 데이터를 저장
                            insertGiftDetails(giftDetails)
                        }
                    }
                } else {
                    throw Exception("Failed to fetch gifts: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Room에 데이터를 저장
    private suspend fun insertGiftDetails(giftDetails: List<GiftDetail>) {
        withContext(Dispatchers.IO) {
            giftDetailDao.insertGiftDetails(giftDetails)
        }
    }

    // 모든 데이터를 삭제
    suspend fun deleteAllGiftDetails() {
        withContext(Dispatchers.IO) {
            giftDetailDao.deleteAll()
        }
    }
}
