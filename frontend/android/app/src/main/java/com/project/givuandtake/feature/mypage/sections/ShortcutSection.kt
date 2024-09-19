package com.project.givuandtake.feature.mypage.sections

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R

@Composable
fun Shortcut() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0XFFFFFFFF), // 흰색 배경
        modifier = Modifier
            .fillMaxWidth() // 상자 너비 맞춤
            .padding(horizontal = 8.dp) // 좌우에 약간의 패딩 추가
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // 아이콘 간 간격
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShortcutItem(imageRes = R.drawable.donation, text = "기부내역")
            ShortcutItem(imageRes = R.drawable.likes, text = "찜 목록")
            ShortcutItem(imageRes = R.drawable.creditcard, text = "카드")
            ShortcutItem(imageRes = R.drawable.address, text = "주소록")
        }
    }
}

@Composable
fun ShortcutItem(@DrawableRes imageRes: Int, text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally, // 아이콘과 텍스트를 중앙에 정렬
        verticalArrangement = Arrangement.Center
    ) {
        // 로컬 리소스 이미지 로드
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = text,
            modifier = Modifier.size(40.dp) // 이미지 크기 설정
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
    }
}
