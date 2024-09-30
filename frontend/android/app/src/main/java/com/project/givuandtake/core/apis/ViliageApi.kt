package com.project.givuandtake.core.apis

import com.project.givuandtake.core.data.SignUpRequest
import com.project.givuandtake.core.data.VillageData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ViliageApiService {
    @GET("experience-village")
    suspend fun getExperienceVillage(
        @Query("sido") sido: String,
        @Query("sigungu") sigungu: String,
        @Query("division") division: String? = null,
        @Query("pageNo") pageNo: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): Response<VillageData>
}


// Retrofit 인스턴스 생성
object ViliageApi {
    private const val BASE_URL = "https://j11e202.p.ssafy.io/api/"

    val api: ViliageApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViliageApiService::class.java)
    }
}