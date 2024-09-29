package com.project.givuandtake.core.apis

import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.data.GiftDetailResponse
import com.project.givuandtake.core.data.GiftResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GiftApiService {

    // 코루틴을 사용한 비동기 API 호출
    @GET("/api/gifts")
    suspend fun getGifts(
        @Header("Authorization") token: String
    ): Response<GiftResponse> // suspend 함수로 변경하여 코루틴에서 사용 가능

    @GET("/api/gifts/{giftIdx}")
    suspend fun getGiftDetail(
        @Header("Authorization") token: String,
        @Path("giftIdx") giftIdx: Int
    ): Response<GiftDetailResponse> // suspend 함수로 변경하여 코루틴에서 사용 가능
}

