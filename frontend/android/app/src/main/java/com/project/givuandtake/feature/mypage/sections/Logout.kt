package com.project.givuandtake.feature.mypage.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Logout() {
    Text(
        text = "로그아웃",
        color = Color.Red, // 빨간색 적용
        fontWeight = FontWeight.Bold, // 굵은 글꼴
        fontSize = 16.sp // 크기 (선택 사항, 필요에 따라 변경 가능)
    )
}