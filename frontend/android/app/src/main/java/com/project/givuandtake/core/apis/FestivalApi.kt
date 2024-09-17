//package com.project.givuandtake.core.apis
//
//import com.project.givuandtake.core.data.FestivalMainData
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface FestivalApiService {
//    @GET("openapi/tn_pubr_public_cltur_fstvl_api")
//    fun getFestivals(
//        @Query("serviceKey") serviceKey: String,
//        @Query("pageNo") pageNo: Int,
//        @Query("numOfRows") numOfRows: Int,
//        @Query("type") type: String = "json"
//    ): Call<FestivalMainData>
//}