package com.project.givuandtake.feature.payment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.FormBody
import okhttp3.Response
import android.content.Intent
import android.net.Uri
import org.json.JSONObject

// 카카오페이 결제 준비 API 요청 함수
fun requestKakaoPay(
    amount: Int,
    itemName: String,
    itemLocation: String,
    onSuccess: (String) -> Unit,
    onError: (Throwable) -> Unit
) {
    val client = OkHttpClient()

    // API 요청을 위한 파라미터 설정
    val body = FormBody.Builder()
        .add("cid", "TC0ONETIME") // 테스트용 cid
        .add("partner_order_id", "order_id_1234") // 주문번호
        .add("partner_user_id", "user_id_1234") // 사용자 ID
        .add("item_name", itemName) // 상품명
        .add("quantity", "1") // 상품 수량
        .add("total_amount", amount.toString()) // 결제 금액
        .add("tax_free_amount", "0") // 면세 금액
        .add("approval_url", "https://your-site.com/success") // 성공 시 리다이렉트 URL
        .add("cancel_url", "https://your-site.com/cancel") // 취소 시 리다이렉트 URL
        .add("fail_url", "https://your-site.com/fail") // 실패 시 리다이렉트 URL
        .build()

    val request = Request.Builder()
        .url("https://kapi.kakao.com/v1/payment/ready")
        .post(body)
        .addHeader("Authorization", "SECRET_KEY") // 관리자 키 추가
        .build()

    CoroutineScope(Dispatchers.IO).launch {6
        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                val redirectUrl = extractRedirectUrl(responseData)
                onSuccess(redirectUrl)
            } else {
                onError(Exception("결제 준비 요청 실패"))
            }
        } catch (e: Exception) {
            onError(e)
        }
    }
}

// 응답 데이터에서 next_redirect_pc_url 추출
fun extractRedirectUrl(responseData: String?): String {
    val json = JSONObject(responseData)
    return json.getString("next_redirect_pc_url")
}



fun approveKakaoPay(tid: String, pgToken: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
    val client = OkHttpClient()

    val body = FormBody.Builder()
        .add("cid", "TC0ONETIME")
        .add("tid", tid)
        .add("partner_order_id", "order_id_1234")
        .add("partner_user_id", "user_id_1234")
        .add("pg_token", pgToken)
        .build()

    val request = Request.Builder()
        .url("https://kapi.kakao.com/v1/payment/approve")
        .post(body)
        .addHeader("Authorization", "KakaoAK your_admin_key")
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                onSuccess()
            } else {
                onError(Exception("결제 승인 요청 실패"))
            }
        } catch (e: Exception) {
            onError(e)
        }
    }
}
