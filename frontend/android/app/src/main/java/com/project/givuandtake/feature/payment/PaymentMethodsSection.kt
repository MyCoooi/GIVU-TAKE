package com.project.givuandtake.feature.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R

@Composable
fun PaymentMethods() {
    var selectedMethod by remember { mutableStateOf("") } // 선택된 결제 수단 상태

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 좌우 16dp, 상하 8dp의 패딩 추가
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Column 내부에도 패딩 추가 가능
        ) {
            Text(
                text = "결제 수단",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 결제 수단을 두 개씩 나란히 배치
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PaymentMethodButtonWithIcon(
                        "신용 / 체크 카드",
                        R.drawable.logo,
                        Modifier.weight(1f),
                        selected = selectedMethod == "신용 / 체크 카드", // 선택 상태 전달
                        onClick = { selectedMethod = "신용 / 체크 카드" } // 선택된 결제 수단 업데이트
                    )
                    PaymentMethodButtonWithIcon(
                        "카카오 페이",
                        R.drawable.kakao,
                        Modifier.weight(1f),
                        selected = selectedMethod == "카카오 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "카카오 페이" } // 선택된 결제 수단 업데이트
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PaymentMethodButtonWithIcon(
                        "네이버 페이",
                        R.drawable.naver,
                        Modifier.weight(1f),
                        selected = selectedMethod == "네이버 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "네이버 페이" } // 선택된 결제 수단 업데이트
                    )
                    PaymentMethodButtonWithIcon(
                        "토스 페이",
                        R.drawable.toss_logo,
                        Modifier.weight(1f),
                        selected = selectedMethod == "토스 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "토스 페이" } // 선택된 결제 수단 업데이트
                    )
                }

                // 신용/체크 카드나 카카오 페이가 선택된 경우에만 카드사 선택 표시
                if (selectedMethod == "신용 / 체크 카드" || selectedMethod == "카카오 페이") {
                    CardSelectionDropdown()
                }
            }
        }
    }
}

@Composable
fun PaymentMethodButtonWithIcon(
    methodName: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    selected: Boolean, // 선택 상태를 인자로 추가
    onClick: () -> Unit // 클릭 이벤트를 추가
) {
    val backgroundColor = if (selected) Color(0xFFDAEBFD) else Color(0xFFFFFFFF) // 선택 여부에 따라 배경색 변경

    Box(
        modifier = modifier
            .clickable(onClick = onClick) // 클릭 이벤트 추가
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color(0xFFB3C3F4)), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = methodName,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified // 아이콘에 색상을 적용하지 않음, 원래 색상 유지
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = methodName,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CardSelectionDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf("카드사 선택") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }, // Text와 Icon을 같이 클릭할 수 있게 설정
            verticalAlignment = Alignment.CenterVertically, // 아이콘과 텍스트를 수직으로 중앙 정렬
            horizontalArrangement = Arrangement.SpaceBetween // 양 끝으로 배치
        ) {
            Text(
                text = selectedCard,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Dropdown Arrow",
                tint = Color.Gray // 화살표 색상 설정
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                selectedCard = "국민카드"
                expanded = false
            }) {
                Text("국민카드")
            }
            DropdownMenuItem(onClick = {
                selectedCard = "우리카드"
                expanded = false
            }) {
                Text("우리카드")
            }
            DropdownMenuItem(onClick = {
                selectedCard = "신한카드"
                expanded = false
            }) {
                Text("신한카드")
            }
        }
    }
}
