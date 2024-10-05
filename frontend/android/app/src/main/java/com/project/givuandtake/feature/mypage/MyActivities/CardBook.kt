package com.project.givuandtake.feature.mypage.MyActivities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun CardBook(navController: NavController) {
    Column() {
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
                text = "카드",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Text(
            text = "카드 등록하기",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 25.dp, top=20.dp)
        )

        Button(
            onClick = { navController.navigate("cardregistration") },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD9D9D9)), // 버튼의 배경색
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_control_point_24),
                contentDescription = "Clear Text",
                tint = Color(0xFFA093DE),
                modifier = Modifier
                    .size(60.dp)
            )
        }

        Divider(
            color = Color(0xFFF2F2F2), // Set the line color to gray
            thickness = 15.dp, // Set the thickness of the line
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // Optional padding to space it
        )

        Text(
            text = "내 카드",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 25.dp, top=20.dp)
        )
    }
}