package com.project.givuandtake.feature.mypage.MyDonation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CategoryFundingDateBar(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    val categories = listOf("최근 6개월", "최근 1년", "전체 보기")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEachIndexed { index, category ->
            val isSelected = selectedCategory == index

            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFFA093DE) else Color(0xFFB3C3F4), label = ""
            )
            val borderColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFFA093DE) else Color.Transparent, label = ""
            )
            val boxWeight by animateFloatAsState(
                targetValue = if (isSelected) 2f else 1f, label = ""
            )
            val fontSize by animateDpAsState(
                targetValue = if (isSelected) 17.dp else 12.dp, label = ""
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(boxWeight)
                    .height(30.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(backgroundColor)
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .clickable { onCategorySelected(index) }
            ) {
                Text(
                    text = category,
                    color = Color.White,
                    fontSize = fontSize.value.sp,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun FundingDetails(navController: NavController) {
    var selectedCategory by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "펀딩내역",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        CategoryFundingDateBar(selectedCategory = selectedCategory, onCategorySelected = {
            selectedCategory = it
        })

        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .border(
                    width = 4.dp,
                    color = Color(0xFFB3A0F2),
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "펀딩금 총액",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "123,242,234₩",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Divider(
            color = Color(0xFFF2F2F2), // Set the line color to gray
            thickness = 15.dp, // Set the thickness of the line
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // Optional padding to space it
        )

        Text("2023.09.23 ~ 2024.09.23", modifier = Modifier.padding(8.dp))

    }
}