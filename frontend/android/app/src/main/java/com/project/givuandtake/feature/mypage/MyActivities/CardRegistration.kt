package com.project.givuandtake.feature.mypage.MyActivities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun CardRegistration(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3E3E3)),
        horizontalAlignment = Alignment.CenterHorizontally, // Column 내 수평 정렬
        verticalArrangement = Arrangement.SpaceBetween // Column 내 요소들의 간격을 일정하게
    ) {
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
                text = "      ",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 카드 모양 상자
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 180.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .border(BorderStroke(1.dp, Color.Black))
        )

        // 안내 텍스트
        Text(
            text = "사각형 안에 카드를 맞추세요",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally) // 가운데 정렬
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate("cardcustomregistration") },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
                .height(48.dp)
        ) {
            Text(
                text = "수동으로 카드 입력",
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Button(
            onClick = { /* 수동 입력 처리 */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .size(56.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_flashlight_on_24),
                contentDescription = "Clear Text",
                tint = Color(0xFFA093DE),
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}


