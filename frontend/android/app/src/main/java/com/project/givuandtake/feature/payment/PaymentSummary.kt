package com.project.givuandtake.feature.payment

import android.content.Intent
import android.icu.util.CurrencyAmount
import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R
import com.project.givuandtake.core.data.PaymentInfo

@Composable
fun PaymentTotalAndButton() {
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
            PaymentTotal()

            Spacer(modifier = Modifier.width(24.dp)) // 총 금액과 버튼 사이 간격 조정

            // 결제하기 버튼을 배치
            PaymentButton()
        }
    }
}


@Composable
fun PaymentTotal() {
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
            text = "50,000 원",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, // 굵은 텍스트
            color = Color(0xFF000000) // 검정색
        )
    }
}

@Composable
fun PaymentButton() {
    // 결제하기 버튼
    Button(
        onClick = { /* 결제 처리 동작 */ },
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
fun PaymentTotalAndButton_gift(paymentInfo: PaymentInfo) {
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
            PaymentTotal_gift(paymentInfo.amount)

            Spacer(modifier = Modifier.width(24.dp))

            // 결제하기 버튼을 배치 (paymentInfo 전달)
            PaymentButton_gift(paymentInfo = paymentInfo)
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
fun PaymentButton_gift(paymentInfo: PaymentInfo) {
    Button(
        onClick = {
            if (paymentInfo.selectedMethod == "카카오 페이") {
                // 카카오페이 결제 준비 요청 호출
                requestKakaoPay(
                    amount = paymentInfo.amount,
                    itemName = paymentInfo.name,
                    itemLocation = paymentInfo.location,
                    onSuccess = { redirectUrl ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
                        // 결제 창 열기
                    },
                    onError = { error ->
                        // 에러 처리
                    }
                )
            }
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
