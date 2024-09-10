package com.project.givuandtake.feature.gift.mainpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier

@Composable
fun DonationPageDetail(itemIndex: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("상세 페이지", color = Color.Black) },
                backgroundColor = Color(0xFFDAEBFD)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "선택한 물품 번호: $itemIndex", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "물품 상세 정보를 여기에 표시합니다.")
        }
    }
}
