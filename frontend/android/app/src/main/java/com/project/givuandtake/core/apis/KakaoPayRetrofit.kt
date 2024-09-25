package com.project.givuandtake.core.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoPayRetrofit {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://open-api.kakaopay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: KakaoPayApi by lazy {
        retrofit.create(KakaoPayApi::class.java)
    }
}