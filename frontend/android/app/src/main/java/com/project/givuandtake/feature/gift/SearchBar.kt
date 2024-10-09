package com.project.givuandtake.feature.gift

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.project.givuandtake.R

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp)) // 검색창 둥근 테두리 적용
            .border(1.dp, Color.Black, shape = RoundedCornerShape(24.dp)) // 테두리
            .padding(horizontal = 16.dp), // 내부 패딩
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24), // 검색 아이콘 리소스
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // 검색 아이콘과 텍스트 필드 사이 간격
            TextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                placeholder = { Text("검색어를 입력하세요") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent, // 배경 투명
                    focusedIndicatorColor = Color.Transparent, // 포커스 시 인디케이터 제거
                    unfocusedIndicatorColor = Color.Transparent // 포커스 해제 시 인디케이터 제거
                ),
                singleLine = true
            )
        }
    }
}
