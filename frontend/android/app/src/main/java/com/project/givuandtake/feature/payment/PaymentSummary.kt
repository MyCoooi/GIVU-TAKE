package com.project.givuandtake.feature.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.givuandtake.core.data.KakaoPaymentInfo
import com.project.givuandtake.core.data.KakaoPaymentInfo_funding

@Composable
fun PaymentTotalAndButton(
    currentAmount: Int,
    kakaoPaymentInfo_funding: KakaoPaymentInfo_funding,
    navController: NavController,
    viewModel: PaymentViewModel = viewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0XFFFAFAFA), // 배경색을 회색으로 설정
        shape = RoundedCornerShape(8.dp) // 모서리 둥글게 설정 가능
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 내부 패딩 추가
            horizontalArrangement = Arrangement.Center, // 중앙에 배치
            verticalAlignment = Alignment.CenterVertically // 수직 가운데 정렬
        ) {
            // 결제 총 금액을 중앙에 배치
            PaymentTotal(currentAmount)

            Spacer(modifier = Modifier.width(24.dp)) // 총 금액과 버튼 사이 간격 조정

            // 결제하기 버튼을 배치, viewModel을 전달
            PaymentButton(kakaoPaymentInfo_funding = kakaoPaymentInfo_funding, navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun PaymentTotal(currentAmount: Int) {
    // 결제 총 금액 부분을 중앙으로 배치
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 중앙 정렬
        modifier = Modifier
            .padding(end = 16.dp) // 결제 버튼과의 여백 추가
    ) {
        Text(
            text = "결제 총 금액",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold, // 정규 두께
            color = Color(0xFFB3C3F4) // 연한 파란색
        )
        Spacer(modifier = Modifier.height(4.dp)) // 텍스트 사이 간격
        Text(
            text = "${String.format("%,d", currentAmount)} 원",  // 금액을 문자열로 변환하여 출력
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, // 굵은 텍스트
            color = Color(0xFF000000) // 검정색
        )
    }
}

@Composable
fun PaymentButton(kakaoPaymentInfo_funding: KakaoPaymentInfo_funding, navController: NavController, viewModel: PaymentViewModel) {
    val context = LocalContext.current // 현재 컨텍스트 가져오기

    Button(
        onClick = {
            // 스프링 서버로 결제 준비 요청을 보냄
            viewModel.preparePayment_funding(
                navController = navController,
                context = context,
                paymentInfo = kakaoPaymentInfo_funding
            )
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(50.dp) // 버튼 높이 설정
            .width(150.dp), // 버튼 너비를 적절하게 설정
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A6CE3))
    ) {
        Text(
            text = "결제하기",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



// 답례품 관련 결제 함수

@Composable
fun PaymentTotalAndButton_gift(
    kakaoPaymentInfo: KakaoPaymentInfo,
    navController: NavController,
    viewModel: PaymentViewModel // ViewModel 추가
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0XFFFAFAFA),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 결제 총 금액을 중앙에 배치
            PaymentTotal_gift(kakaoPaymentInfo.amount)

            Spacer(modifier = Modifier.width(24.dp))

            // 결제하기 버튼에 viewModel을 전달
            PaymentButton_gift(
                kakaoPaymentInfo = kakaoPaymentInfo,
                navController = navController,
                viewModel = viewModel // ViewModel 전달
            )
        }
    }
}






@Composable
fun PaymentTotal_gift(amount: Int) {
    // 결제 총 금액 부분을 중앙으로 배치
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 중앙 정렬
        modifier = Modifier
            .padding(end = 16.dp) // 결제 버튼과의 여백 추가
    ) {
        Text(
            text = "결제 총 금액",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold, // 정규 두께
            color = Color(0xFFB3C3F4) // 연한 파란색
        )
        Spacer(modifier = Modifier.height(4.dp)) // 텍스트 사이 간격
        Text(
            text = "$amount 원",  // 금액을 문자열로 변환하여 출력
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, // 굵은 텍스트
            color = Color(0xFF000000) // 검정색
        )
    }
}

@Composable
fun PaymentButton_gift(kakaoPaymentInfo: KakaoPaymentInfo, navController: NavController, viewModel: PaymentViewModel) {
    val context = LocalContext.current // 현재 컨텍스트 가져오기

    Button(
        onClick = {
            // 스프링 서버로 결제 준비 요청을 보냄
            viewModel.preparePayment(navController = navController, context = context, paymentInfo = kakaoPaymentInfo)
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(50.dp)
            .width(150.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A6CE3))
    ) {
        Text(
            text = "결제하기",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

//@Composable
//fun PaymentButton_gift(kakaoPaymentInfo: KakaoPaymentInfo, navController: NavController) {
//    val context = LocalContext.current // 현재 컨텍스트 가져오기
//    val kakaoPayManager = remember { KakaoPayManager() } // KakaoPayManager 객체 생성
//
//    Button(
//        onClick = {
//            // 결제 준비 함수 호출
//            kakaoPayManager.prepareKakaoPay(
//                navController = navController,
//                context = context,
//                kakaoPaymentInfo = kakaoPaymentInfo)
//        },
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .height(50.dp)
//            .width(150.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A6CE3))
//    ) {
//        Text(
//            text = "결제하기",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//    }
//}



