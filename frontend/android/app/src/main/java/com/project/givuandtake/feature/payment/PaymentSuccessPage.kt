package com.project.givuandtake.feature.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.project.givuandtake.core.apis.Address.AddressApi
import com.project.givuandtake.core.data.Address.AddressData
import com.project.givuandtake.core.data.Address.UserAddress
import com.project.givuandtake.core.data.KakaoPaymentInfo
import com.project.givuandtake.core.datastore.TokenManager
import com.project.givuandtake.feature.mypage.MyActivities.AddressViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentSuccessPage(
    navController: NavController,
    paymentInfoJson: String // JSON 형태로 전달받은 paymentInfo
) {
    val currentDestination = navController.currentDestination
    Log.d("Current Destination", "Current Destination: ${currentDestination?.label}")

    // paymentInfoJson을 KakaoPaymentInfo 객체로 변환
    val paymentInfo = if (paymentInfoJson.isNotEmpty()) {
        remember {
            Gson().fromJson(paymentInfoJson, KakaoPaymentInfo::class.java)
        }
    } else {
        null // deep link로 들어온 경우
    }
    Log.d("sucesspage","paymentInfo : ${paymentInfo}")


    val context = LocalContext.current
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"

    val viewModel: AddressViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchUserAddresses(accessToken)
    }

    val addresses by viewModel.addresses

    Log.d("sucesspage","sussess_addresses : ${addresses}")


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("결제 성공") },
                backgroundColor = Color(0xFFB3C3F4), // 배경색 변경 (보라색)
                contentColor = Color.White // 텍스트 색상 변경 (흰색)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "결제가 성공적으로 완료되었습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // 홈으로 이동하거나 다른 페이지로 이동
                    navController.navigate("gift")
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFA093DE), // 배경색을 보라색으로 설정
                        contentColor = Color.White // 텍스트 색상을 흰색으로 설정
                    )) {
                    Text(text = "홈으로 돌아가기")
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentSuccessPagePreview() {
    // 실제 NavController 대신 가짜 NavController 사용
    val fakeNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("결제 성공") },
                backgroundColor = Color(0xFFB3C3F4), // 배경색 변경 (보라색)
                contentColor = Color.White // 텍스트 색상 변경 (흰색)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "결제가 성공적으로 완료되었습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // 홈으로 이동하거나 다른 페이지로 이동
//                    navController.navigate("gift")
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFA093DE), // 배경색을 보라색으로 설정
                        contentColor = Color.White // 텍스트 색상을 흰색으로 설정
                    )) {
                    Text(text = "홈으로 돌아가기")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewPaymentSuccessPage() {
    PaymentSuccessPagePreview()
}
