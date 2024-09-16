package com.project.givuandtake.feature.attraction

import MarketItem
import SquareMarketItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMarketTab() {
    Text(
        text = "우리 고향 정기 전통시장",
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Market 정보
    Column {
        MarketItem(
            marketName = "관양시장",
            address = "동안구 관평로 336번길 31",
            parkingAvailable = true,
            restroomAvailable = true
        )
        MarketItem(
            marketName = "석수시장",
            address = "만안구 석천로 171번길 20",
            parkingAvailable = true,
            restroomAvailable = false
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "우리 고향 상설 전통시장",
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // 의정부시장과 같은 정사각형 박스 추가
    Row(
    modifier = Modifier
    .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SquareMarketItem(
            marketName = "의정부시장",
            address = "시민로 143번길 40",
            parkingAvailable = true,
            restroomAvailable = true
        )
        SquareMarketItem(
            marketName = "망미동시장",
            address = "동안구 관평로 336번길 31",
            parkingAvailable = true,
            restroomAvailable = true
        )
    }

}