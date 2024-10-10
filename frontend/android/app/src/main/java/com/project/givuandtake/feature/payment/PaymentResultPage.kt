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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.project.givuandtake.core.data.KakaoPaymentInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentResultPage(
    navController: NavController,
    paymentInfo: KakaoPaymentInfo // 전달받은 결제 정보
) {
    val context = LocalContext.current
    val intent = (context as? Activity)?.intent
    val uri = intent?.data

    // Gson 객체 생성
    val gson = remember { Gson() }
    // paymentInfo 객체를 JSON 문자열로 변환
    val paymentInfoJson = gson.toJson(paymentInfo)

    LaunchedEffect(uri) {

        delay(3000L) // 5초 대기
        Log.d("uri_pay:","uri : ${uri}")
        Log.d("uri_pay:","intent : ${intent}")
        // 결제 성공 페이지로 이동하면서 paymentInfoJson 전달
        navController.navigate("payment_success/$paymentInfoJson")
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentResultPagePreview() {
    // 가짜 NavController 대체
    val fakeNavController = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("결제 대기 중") },
                backgroundColor = Color(0xFFB3C3F4), // 배경색을 보라색으로 설정 (커스텀 색상)
                )
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

    // 가짜 네비게이션을 실행하는 프리뷰
    LaunchedEffect(Unit) {
        fakeNavController.launch {
            // 5초 후에 결제 성공 페이지로 이동
            delay(5000L)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPaymentResultPage() {
    PaymentResultPagePreview()
}