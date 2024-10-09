package com.project.givuandtake.feature.gift

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.givuandtake.core.data.GiftDetail


@Composable
fun GiftListScreen(
    categoryIdx: Int,
    navController: NavController,
    viewModel: GiftViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val categoryGiftsState = viewModel.categoryGiftDetails.collectAsState()
    val categoryGifts = categoryGiftsState.value

    val loadingState = viewModel.loading.collectAsState()
    val loading = loadingState.value

    val errorState = viewModel.error.collectAsState()
    val error = errorState.value

    // 카테고리 인덱스에 따른 카테고리 이름 매핑
    val categoryName = when (categoryIdx) {
        1 -> "지역상품권"
        2 -> "농축산물"
        3 -> "수산물"
        4 -> "가공식품"
        5 -> "공예품"
        else -> "알 수 없는 카테고리"
    }

    LaunchedEffect(categoryIdx) {
        viewModel.fetchGiftsByCategory(categoryIdx)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3C3F4)) // 상단 파란색 배경
    ) {
        // 상단 디자인
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFB3C3F4)), // 파란색 배경 설정
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = categoryName, // 선택된 카테고리 이름 표시
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // 상단과 리스트 사이 간격

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFFFFFFF), // 하얀 배경색 설정
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp) // 상단을 둥글게 설정
                )
                .padding(horizontal = 16.dp) // 전체 내부 패딩 설정
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text("Error: $error")
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), // 2열 설정
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp), // 세로 간격 설정
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // 가로 간격 설정
                    ) {
                        items(categoryGifts) { gift ->
                            GiftItem(gift = gift, navController = navController) // 상품 아이템
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun GiftItem(gift: GiftDetail, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                // 클릭 시 상세 페이지로 이동
                navController.navigate("gift_page_detail/${gift.giftIdx}")
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 상품 이미지
            Image(
                painter = rememberImagePainter(gift.giftThumbnail),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            // 상품명
            Text(
                text = gift.giftName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1, // 한 줄로 표시
                overflow = TextOverflow.Ellipsis, // 글자 수 초과 시 ... 표시
                modifier = Modifier.padding(start = 4.dp)
            )

            // 상품 위치
            Text(
                text = gift.location,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )

            // 가격
//            Text(
//                text = "${gift.price}원",
//                fontWeight = FontWeight.Bold,
//                fontSize = 14.sp,
//                color = Color.Black,
//                modifier = Modifier.padding(start = 4.dp)
//            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)) // 가격 버튼을 둥글게 설정
                    .background(Color(0xFFD1E9FF)) // 연한 파란색 배경
                    .padding(horizontal = 16.dp, vertical = 8.dp), // 패딩 설정
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "₩${gift.price}원",
                    fontSize = 14.sp,
                    color = Color.Black // 텍스트 색상 설정
                )
            }
        }
    }
}