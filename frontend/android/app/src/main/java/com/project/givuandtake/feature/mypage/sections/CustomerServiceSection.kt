package com.project.givuandtake.feature.mypage.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomerServiceTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp), // 고객센터 제목에만 패딩 적용
        color = Color(0XFFA093DE)
    )
}


@Composable
fun CustomerServiceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CustomerServiceItem("공지사항", Icons.Default.Info) // 공지사항
        Spacer(modifier = Modifier.height(12.dp))
        CustomerServiceItem("1:1 문의", Icons.Default.Email) // 1:1 문의
        Spacer(modifier = Modifier.height(12.dp))
        CustomerServiceItem("자주 묻는 질문", Icons.AutoMirrored.Default.ExitToApp) // 자주 묻는 질문

        Spacer(modifier = Modifier.height(12.dp))

        // 시간 정보
        Text(
            text = "운영시간 : 평일 09:00 ~ 18:00\n(점심시간 12:00 ~ 13:00 제외)",
            fontSize = 14.sp,
            color = Color(0xFF888888),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
    }
}


@Composable
fun CustomerServiceItem(title: String, icon: ImageVector) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,  // 배경을 흰색으로 설정
        border = BorderStroke(3.dp, Color(0xFFB3C3F4)), // 테두리 색상과 두께 설정
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB3C3F4) // 아이콘 색상 설정
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}
