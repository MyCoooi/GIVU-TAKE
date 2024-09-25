package com.project.givuandtake.feature.mypage.MyActivities

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun AddressBook(navController: NavController) {


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
                text = "주소록",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f), fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .border(
                    border = BorderStroke(2.dp, Color(0xFFA093DE)), // 테두리 색과 두께
                    shape = RoundedCornerShape(15.dp) // 둥근 테두리
                )
                .background(Color.White, shape = RoundedCornerShape(8.dp)) // 배경색과 모양
                .padding(16.dp) // 내부 여백
                .fillMaxWidth()
                .clickable { navController.navigate("addresssearch") }
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24), // R.drawable에서 아이콘 참조
                    contentDescription = "Icon", // 아이콘 설명
                    tint = Color(0xFFA093DE), // 아이콘 색상
                    modifier = Modifier.size(24.dp) // 아이콘 크기
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "주소를 입력하세요",
                    color = Color.Gray, // 텍스트 색상
                    style = MaterialTheme.typography.body1 // 텍스트 스타일
                )
            }
        }
        Box(
            modifier = Modifier.clickable { navController.navigate("addressmapsearch") }
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically, // Row 내 수직 정렬
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_gps_fixed_24), // R.drawable에서 아이콘 참조
                    contentDescription = "Icon", // 아이콘 설명
                    tint = Color(0xFFA093DE), // 아이콘 색상
                    modifier = Modifier.size(24.dp) // 아이콘 크기

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "현재 위치로 선정",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24), // R.drawable에서 아이콘 참조
                    contentDescription = "Icon", // 아이콘 설명
                    tint = Color(0xFFA093DE), // 아이콘 색상
                    modifier = Modifier.size(24.dp) // 아이콘 크기

                )
            }
        }
    }
}

//devU01TX0FVVEgyMDI0MDkyNDIzMTE1NTExNTEwODI=