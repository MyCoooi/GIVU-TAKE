package com.project.givuandtake.feature.gift

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.givuandtake.core.data.GiftDetail

@Composable
fun GiftListScreen(categoryIdx: Int, viewModel: GiftViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // collectAsState()의 결과를 직접 변수에 저장
    // StateFlow 값을 collectAsState로 변환하고 value로 접근
    val categoryGiftsState = viewModel.categoryGiftDetails.collectAsState()
    val categoryGifts = categoryGiftsState.value

    Log.d("category","itemlist : ${categoryGifts}")
    val loadingState = viewModel.loading.collectAsState()
    val loading = loadingState.value

    val errorState = viewModel.error.collectAsState()
    val error = errorState.value

    LaunchedEffect(categoryIdx) {
        viewModel.fetchGiftsByCategory(categoryIdx)
    }

    when {
        loading -> {
            CircularProgressIndicator()
        }
        error != null -> {
            Text("Error: $error")
        }
        else -> {
            LazyColumn {
                items(categoryGifts) { gift ->
                    GiftItem(gift = gift)
                }
            }
        }
    }
}

@Composable
fun GiftItem(gift: GiftDetail) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = gift.giftName, style = MaterialTheme.typography.h6)
        gift.giftThumbnail?.let {
            Image(painter = rememberImagePainter(it), contentDescription = null)
        }
        Text(text = gift.location)
        Text(text = "${gift.price}원")
    }
}
