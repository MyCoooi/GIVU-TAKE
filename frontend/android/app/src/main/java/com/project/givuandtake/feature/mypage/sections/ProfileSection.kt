package com.project.givuandtake.feature.mypage.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProfileSection() {
    // 프로필과 기부 정보를 포함하는 박스
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFB3C3F4), // 연한 파란색 배경
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 프로필 정보 (이름만 표시)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp) // 왼쪽에 12.dp padding 추가
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "프로필",
                    modifier = Modifier.size(50.dp),
                    tint = Color(0xFFFFFFFF) // 진한 회색
                )

                Spacer(modifier = Modifier.width(16.dp))

                // 프로필 이름
                Text(
                    text = "김프로님",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 기부 요약 정보 (나의 기부액, 참여한 펀딩 수)
            DonationSummaryCard()
        }
    }
}

@Composable
fun DonationSummaryCard() {
    // 기부 요약 정보 카드
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0XFFFBFAFF), // 흰색 배경
        modifier = Modifier
            .fillMaxWidth() // 상자 너비 맞춤
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
        ) {
            // "나의 기부액"과 "999,999원" 가로로 나란히 정렬하면서 세로 중앙 맞춤
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // 세로 중앙 정렬
                horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝으로 정렬
            ) {
                Text(
                    text = "나의 기부액",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
                Text(
                    text = "999,999원",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // "참여한 펀딩 수"와 "3건" 가로로 나란히 정렬하면서 세로 중앙 맞춤
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // 세로 중앙 정렬
                horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝으로 정렬
            ) {
                Text(
                    text = "참여한 펀딩 수",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
                Text(
                    text = "3건",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
            }
        }
    }
}

