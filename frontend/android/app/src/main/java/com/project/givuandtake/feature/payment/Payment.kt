package com.project.payment

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R
import com.project.givuandtake.feature.payment.AmountButtonsRow
import com.project.givuandtake.feature.payment.AmountInputField
import com.project.givuandtake.feature.payment.PaymentMethods
import com.project.givuandtake.feature.payment.PaymentTotalAndButton
import com.project.givuandtake.feature.payment.TopBar

@Composable
fun PaymentScreen(navController: NavController) {
    val scrollState = rememberScrollState() // 스크롤 상태 기억

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
            PaymentProjectInfo()

            Spacer(modifier = Modifier.height(24.dp))

            // Payment methods section
            PaymentMethods()

            Spacer(modifier = Modifier.height(32.dp))

            // 결제 총 금액 및 버튼 섹션
            PaymentTotalAndButton()

            // 불필요한 추가 여백을 방지하기 위해 Spacer 제거
            // Spacer(modifier = Modifier.height(64.dp)) <- 이 부분을 제거
        }
    }
}



@Composable
fun PaymentProjectInfo() {
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
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
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
                        text = "재난 재해",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "태풍 매미 재난 지원 사업",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    // 경상남도 합천군 텍스트와 아이콘을 함께 Row에 배치
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
                            text = "경상남도 합천군",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 분리된 금액 입력 필드 사용
            AmountInputField(
                inputText = if (currentAmount == 0) "" else currentAmount.toString(),
                onInputChange = { newValue -> currentAmount = newValue.toIntOrNull() ?: 0 },
                isFocused = isFocused,
                onFocusChange = { isFocused = it }
            )

            AmountButtonsRow { amountToAdd ->
                currentAmount += amountToAdd
            } // 금액 버튼을 눌렀을 때 호출되는 콜백
        }
    }
}