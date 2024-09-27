package com.project.givuandtake.core.apis

import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.data.GiftResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GiftApiService {
    @GET("/api/gifts")
    fun getGifts(
        @Header("Authorization") token: String
    ): Call<GiftResponse> // API 응답으로 받을 데이터 타입


//    @GET("/api/gifts/{giftIdx}")
//    suspend fun getGiftDetail(
//        @Header("Authorization") token: String,
//        @Path("giftIdx") giftIdx: Int
//    ): Response<GiftDetailResponse> // GiftDetailResponse는 응답 데이터를 위한 데이터 클래스
}
