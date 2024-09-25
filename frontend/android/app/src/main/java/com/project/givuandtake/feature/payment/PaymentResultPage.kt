package com.project.givuandtake.feature.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentResultPage(
    navController: NavController,
    kakaoPayManager: KakaoPayManager // 결제 승인 관리를 위한 KakaoPayManager 객체
) {
    val context = LocalContext.current
    val intent = (context as? Activity)?.intent
    val uri = intent?.data

    LaunchedEffect(uri) {
        // 3초 대기
        delay(10000L)

        // 리다이렉트된 URL에서 pg_token 추출
        val pgToken = uri?.getQueryParameter("pg_token")
        Log.d("KakaopayApi", "pg : ${pgToken}")
        Log.d("KakaopayApi", "uri : ${uri}")


        if (pgToken != null) {
            // pg_token이 있으면 결제 승인 요청
            kakaoPayManager.approveKakaoPay(navController, pgToken)
        } else {
            // pg_token이 없으면 결제 실패 처리
            navController.navigate("payment_success")
        }
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

