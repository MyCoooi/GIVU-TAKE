package com.project.givuandtake.feature.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.project.givuandtake.core.data.KakaoPaymentInfo_funding
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentResultPage_funding(
    navController: NavController,
    paymentInfo: KakaoPaymentInfo_funding // 전달받은 결제 정보
) {
    val context = LocalContext.current
    val intent = (context as? Activity)?.intent
    val uri = intent?.data

    // Gson 객체 생성
    val gson = remember { Gson() }
    // paymentInfo 객체를 JSON 문자열로 변환
    val paymentInfoJson = gson.toJson(paymentInfo)
    Log.d("funding", "funding_result : $paymentInfoJson")

    LaunchedEffect(uri) {
        delay(3000L) // 3초 대기

        // 결제 성공 페이지로 이동하면서 paymentInfoJson 전달
        navController.navigate("payment_success_funding/$paymentInfoJson")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("결제 대기 중") })
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("결제 처리가 진행 중입니다. 잠시만 기다려주세요.")
                    Spacer(modifier = Modifier.height(20.dp))
                    CircularProgressIndicator()
                }
            }
        }
    )
}
