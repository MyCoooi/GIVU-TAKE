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
    navController: NavController
) {
    val context = LocalContext.current
    val intent = (context as? Activity)?.intent
    val uri = intent?.data

    LaunchedEffect(uri) {

        delay(5000L) // 5초 대기

        // 결제 성공 페이지로 이동
        navController.navigate("payment_success")
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


