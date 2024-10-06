package com.project.givuandtake.core.apis

import android.util.Log
import com.project.givuandtake.core.data.KakaoPayReadyResponse2
import com.project.givuandtake.core.data.KakaoPaymentInfo
import retrofit2.Response

class PaymentRepository(private val apiService: PaymentApiService) {

    suspend fun preparePayment(token: String, kakaoPaymentInfo: KakaoPaymentInfo): Response<KakaoPayReadyResponse2> {
        Log.d("kakaopay","토큰:${token}, 정보:${kakaoPaymentInfo}")
        return apiService.preparePayment(token, kakaoPaymentInfo)
    }
}




