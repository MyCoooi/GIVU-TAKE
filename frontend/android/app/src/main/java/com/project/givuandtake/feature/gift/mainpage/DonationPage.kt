package com.project.givuandtake.feature.gift.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DonationPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GIVU & TAKE", color = Color.Black) },
                backgroundColor = Color(0xFFDAEBFD)
            )
        },
        bottomBar = {
            BottomNavBar() // 하단 고정 네비게이션 바
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Scaffold의 패딩을 적용
                .padding(horizontal = 16.dp)
        ) {
            item { 
                Text(text = "우리 고향 기부하기", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(text = "상품종류 순", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                FilterButtons_category()
            }

            item {
                Text(text = "상품가격 순", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                FilterButtons_price()
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(text = "맞춤 추천상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                ProductGrid(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(text = "최고 인기 상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                ProductGrid(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun FilterButtons_category() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween // 아이템들이 한 줄에 균등하게 배치되도록 설정
    ) {
        CategoryButton(text = "지역상품권", icon = painterResource(id = R.drawable.local_product))
        CategoryButton(text = "농축산물", icon = painterResource(id = R.drawable.agriculture_product))
        CategoryButton(text = "수산물", icon = painterResource(id = R.drawable.seafood_product))
        CategoryButton(text = "가공식품", icon = painterResource(id = R.drawable.processed_food))
        CategoryButton(text = "공예품", icon = painterResource(id = R.drawable.craft_product))
    }
}


@Composable
fun CategoryButton(text: String, icon: Painter) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(4.dp) // 최소한의 여백만 유지
    ) {
        // Text positioned above the circle
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 4.dp) // 텍스트와 원형 간 간격 최소화
        )

        // Icon in a circle
        Box(
            modifier = Modifier
                .size(64.dp) // 원형 크기 설정
                .clip(CircleShape)
                .background(Color(0xFFB3C3F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp) // 아이콘 크기
            )
        }
    }
}




@Composable
fun FilterButtons_price() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
            0xFFB3C3F4
        )
        )) {
            Text(text = "10,000 이하")
        }
        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
            0xFFB3C3F4
        )
        )) {
            Text(text = "30,000 이하")
        }
        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
            0xFFB3C3F4
        )
        )) {
            Text(text = "50,000 이하")
        }
    }
}

@Composable
fun ProductGrid(navController: NavController) {
    // 아이템 리스트 생성 (예: 6개의 아이템)
    val items = (0..5).toList()

    // LazyRow를 감싸는 Box에 테두리 추가
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게 설정
            .border(2.dp, Color.Black.copy(alpha = 0.3f)) // 테두리 추가
            .padding(vertical = 8.dp)

    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // 내부 여백 추가
            horizontalArrangement = Arrangement.spacedBy(16.dp) // 카드 간격 설정
        ) {
            // 2개씩 묶어서 슬라이드 되도록 설정
            items(items.chunked(2)) { columnItems ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    columnItems.forEach { index ->
                        ProductCard(index, navController) // navController 전달
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(index: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .clickable { // 클릭 시 상세 페이지로 이동
                navController.navigate("donation_page_detail/$index")
            },  // 카드 크기를 설정
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // 이미지 placeholder
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                LoadImageFromAssets("blank.PNG")  // placeholder 이미지
            }
            Spacer(modifier = Modifier.height(8.dp))
            // 상품명
            Text(text = "물품 $index", fontWeight = FontWeight.Bold)
            // 가격
            Text(text = "$${index * 10000}", color = Color.Gray)
            // 주소
            Text(text = "부산광역시 ${index} 로", color = Color.Gray)
        }
    }
}

@Composable
fun BottomNavBar() {
    BottomAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        Text(text = "홈", modifier = Modifier.padding(16.dp))
        Text(text = "기부", modifier = Modifier.padding(16.dp))
        Text(text = "마이페이지", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "donation_page") {
        composable("donation_page") { DonationPage(navController) }
        composable("donation_page_detail/{itemIndex}") { backStackEntry ->
            val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toInt() ?: 0
            DonationPageDetail(itemIndex)
        }
    }
}




@Preview
@Composable
fun preview() {
    val navController = rememberNavController() // Preview를 위한 NavController 생성
    DonationPage(navController) // DonationPage에 NavController 전달
}
