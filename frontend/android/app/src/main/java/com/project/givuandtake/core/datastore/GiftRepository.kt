package com.project.givuandtake.core.datastore

import android.content.Context
import com.project.givuandtake.core.data.DatabaseProvider
import com.project.givuandtake.core.data.GiftDetail
import kotlinx.coroutines.flow.Flow

class GiftRepository(private val context: Context) {
    private val giftDetailDao = DatabaseProvider.getDatabase(context).giftDetailDao()

    fun getAllGiftDetails(): Flow<List<GiftDetail>> {
        return giftDetailDao.getAllGiftDetails()
    }

    fun getGiftDetailsByIds(ids: List<Int>): Flow<List<GiftDetail>> {
        return giftDetailDao.getGiftDetailsByIds(ids)
    }

    suspend fun insertGiftDetails(giftDetails: List<GiftDetail>) {
        giftDetailDao.insertGiftDetails(giftDetails)
    }

    suspend fun deleteAllGiftDetails() {
        giftDetailDao.deleteAll()
    }
}

