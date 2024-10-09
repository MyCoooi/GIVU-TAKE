package com.project.payment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.project.givuandtake.core.apis.Funding.FundingDetailData
import com.project.givuandtake.core.data.KakaoPaymentInfo
import com.project.givuandtake.core.data.KakaoPaymentInfo_funding
import com.project.givuandtake.feature.payment.AmountButtonsRow
import com.project.givuandtake.feature.payment.AmountInputField
import com.project.givuandtake.feature.payment.PaymentMethods
import com.project.givuandtake.feature.payment.PaymentTotalAndButton
import com.project.givuandtake.feature.payment.TopBar

@Composable
fun PaymentScreen(navController: NavController, fundingDetailJson: String) {
    val scrollState = rememberScrollState() // 스크롤 상태 기억
    val gson = Gson()
    // JSON 문자열을 FundingDetailData 객체로 변환
    val fundingDetail = gson.fromJson(fundingDetailJson, FundingDetailData::class.java)
    Log.d("fundingDetail","fundingDetail : ${fundingDetail}")
    var currentAmount by remember { mutableStateOf(0) } // 현재 금액 상태

    // KakaoPaymentInfo 설정 (필요한 값들을 fundingDetail로부터 가져옵니다)
    var kakaoPaymentInfoFunding by remember {
        mutableStateOf(
            KakaoPaymentInfo_funding(
                fundingIdx = fundingDetail.fundingIdx,
                paymentMethod = "KAKAO", // 결제 수단은 고정 또는 변경 가능
                price = currentAmount // 결제 금액은 사용자가 입력한 값
            )
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White // Set the entire screen background to white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // 스크롤 가능하게 설정
        ) {
            TopBar(navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            // Payment project information section
            PaymentProjectInfo(fundingDetail, onAmountChange = { amount ->
                currentAmount = amount
                kakaoPaymentInfoFunding = kakaoPaymentInfoFunding.copy(price = currentAmount)
            })

            Spacer(modifier = Modifier.height(24.dp))

            // Payment methods section
            PaymentMethods()

            Spacer(modifier = Modifier.height(32.dp))

            // 결제 총 금액 및 버튼 섹션
            PaymentTotalAndButton(
                currentAmount = currentAmount,
                kakaoPaymentInfo_funding = kakaoPaymentInfoFunding,
                navController = navController
            )

            // 불필요한 추가 여백을 방지하기 위해 Spacer 제거
        }
    }
}



@Composable
fun PaymentProjectInfo(fundingDetail: FundingDetailData, onAmountChange: (Int) -> Unit) {
    var inputText by remember { mutableStateOf("") } // 금액 입력 상태
    var isFocused by remember { mutableStateOf(false) } // 포커스 여부를 상태로 저장
    var currentAmount by remember { mutableStateOf(0) } // 현재 금액 상태

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 좌우 16dp, 상하 8dp의 패딩 추가
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 내부 컴포넌트 간의 패딩
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE0E0E0),
                    modifier = Modifier.size(100.dp) // 이미지 크기 조정
                ) {
                    Image(
                        painter = rememberImagePainter(fundingDetail.fundingThumbnail),
                        contentDescription = "펀딩 이미지",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 텍스트들을 이미지 높이에 맞춰서 균등하게 배치
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(100.dp) // 이미지 높이와 맞춤
                ) {
                    Text(
                        text = fundingDetail.fundingTitle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fundingDetail.fundingContent,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    // 위치 정보 표시
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray, // 아이콘 색상
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // 아이콘과 텍스트 사이 간격
                        Text(
                            text = "${fundingDetail.sido} ${fundingDetail.sigungu}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 금액 입력 필드 및 버튼
            AmountInputField(
                inputText = if (currentAmount == 0) "" else currentAmount.toString(),
                onInputChange = { newValue ->
                    currentAmount = newValue.toIntOrNull() ?: 0
                    onAmountChange(currentAmount) // 금액이 변경되면 호출
                },
                isFocused = isFocused,
                onFocusChange = { isFocused = it }
            )

            AmountButtonsRow { amountToAdd ->
                currentAmount += amountToAdd
                onAmountChange(currentAmount) // 버튼으로 금액이 변경되면 호출
            }
        }
    }
}

