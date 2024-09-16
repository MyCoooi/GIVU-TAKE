package com.project.givuandtake.feature.attraction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FestivalItem(
    location: String,
    description: String,
    title: String,
    dateRange: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // 카드들 간의 간격
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp) // 카드 내부 패딩
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 상단 텍스트 (주소 등)
            Text(
                text = location,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))

            // 설명 텍스트
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 타이틀과 날짜를 나란히 배치
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = dateRange,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MainFestivalTab() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "우리 고향 축제",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "전체보기",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(end=8.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

//        LazyColumn {
//            items(5) { // 아이템 수는 필요에 따라 변경
//                FestivalItem(
//                    location = "강원특별자치도 영월군 영월읍 단종로 24",
//                    description = "조선시대국장재현+정순왕후선발대회\n+야간촛불다리기",
//                    title = "단종문화제",
//                    dateRange = "4-28 ~ 04-30"
//                )
//            }
//        }

}