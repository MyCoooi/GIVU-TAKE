//package com.project.givuandtake.core.apis
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitFestivalInstance {
//
//    private const val BASE_URL = "http://api.data.go.kr/"
//
//    val api: FestivalApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(FestivalApiService::class.java)
//    }
//}