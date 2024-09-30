package com.project.givuandtake.core.apis.Funding

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// 댓글 응답 데이터 클래스
data class CommentResponse(
    val success: Boolean,
    val data: List<CommentData>
)

data class CommentData(
    val commentIdx: Int,
    val name: String,
    val commentContent: String,
    val createdDate: String
)

// Retrofit API 정의
interface FundingDetailCommentApiService {
    @GET("government-fundings/{fundingIdx}/comments")
    fun getComments(
        @Path("fundingIdx") fundingIdx: Int
    ): Call<CommentResponse>
}

// Retrofit 인스턴스 생성
object FundingDetailCommentApi {
    private const val BASE_URL = "https://j11e202.p.ssafy.io/api/"

    val api: FundingDetailCommentApiService by lazy {
        // HttpLoggingInterceptor 추가
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // OkHttpClient 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FundingDetailCommentApiService::class.java)
    }
}
