package com.project.givuandtake.feature.mypage.MyActivities

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun UserInfo(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // 상단 회원 정보 타이틀과 뒤로가기 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically, // Row 내 수직 정렬
            horizontalArrangement = Arrangement.SpaceBetween // 좌우 정렬
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
                    .weight(0.3f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "회원 정보",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 이미지 (사용자 아이콘 대신 이미지 사용)
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape) // CircleShape로 전체 박스 모양 설정
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://via.placeholder.com/150", // 여기에 실제 사용자 이미지 URL을 넣으세요
                contentDescription = "User Image",
                modifier = Modifier
                    .size(100.dp) // 이미지 크기를 동일하게 설정
                    .clip(CircleShape) // 이미지에 CircleShape 적용
                    .background(Color.LightGray, CircleShape), // 배경색 적용
                contentScale = ContentScale.Crop // 이미지를 꽉 채우도록 설정
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 정보 (이름, 이메일, 전화번호 등)
        UserInfoItem(label = "이름", value = "진라면")
        DrawLine()
        UserInfoItem(label = "이메일", value = "aisdjlfkaskkdf@naver.com")
        DrawLine()
        UserInfoItem(label = "전화번호", value = "010-1234-5321")
        DrawLine()
        UserInfoItem(label = "성별", value = "남성")
        DrawLine()
        UserInfoItem(label = "생일", value = "2020-01-12")
        DrawLine()


        // 하단 버튼들
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.End // 버튼을 오른쪽에 정렬
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(20.dp)) // 둥근 모서리 적용
                    .background(Color(0xffFBFAFF))
                    .border(1.dp, Color(0XFFA093DE), RoundedCornerShape(20.dp)) // 테두리 적용
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable { /* 비밀번호 변경 로직 */ }
            ) {
                Text(text = "비밀번호 변경", fontSize = 14.sp, color = Color.Black)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xffFBFAFF))
                    .border(1.dp, Color(0XFFA093DE), RoundedCornerShape(20.dp)) // 테두리 적용
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable { /* 회원 정보 수정 로직 */ }
            ) {
                Text(text = "회원정보 수정", fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun UserInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp) // 양쪽 패딩 추가

    ) {
        // Label (예: 이름)
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 8.dp) // 레이블과 값 사이 간격 추가

        )

        // Value (예: 진라면)
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 12.dp)


        )
    }
}


@Composable
fun DrawLine() {
    Canvas(modifier = Modifier.fillMaxWidth()) {
        // Canvas의 너비를 이용하여 선을 그립니다.
        val canvasWidth = size.width
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, 0f),
            end = Offset(canvasWidth, 0f),
            strokeWidth = 2f,
            cap = StrokeCap.Round
        )
    }
}
