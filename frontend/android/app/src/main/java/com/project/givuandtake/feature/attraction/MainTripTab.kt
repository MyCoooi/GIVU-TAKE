package com.project.givuandtake.feature.attraction

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.core.apis.TourismIdRetrofitInstance
import com.project.givuandtake.core.data.TourismItem
import com.project.givuandtake.core.data.TripIdData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getTripIdData(areaCode: Int, sigunguCode: Int, onResult: (List<String>) -> Unit) {
    val serviceKey =
        "ClEl7z%2F9nNW%2Fg0NNpuJsf6wBBPJV5UWiVxKC6SzME5GsWrUpQ85zpxv1aJY4Ockw3%2Bm03%2FeCIYyg60sfOqIOxg%3D%3D"

    TourismIdRetrofitInstance.api.getTourismIdData(
        serviceKey,
        areaCode = areaCode,
        sigunguCode = sigunguCode
    ).enqueue(object : Callback<TripIdData> {
        override fun onResponse(call: Call<TripIdData>, response: Response<TripIdData>) {
            Log.d("TourismAPIdddddd", "Response: ${response.body()}")
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("TourismAPIdd", "Response body: ${responseBody}")

                // contentId만 가져옵니다
                val contentIds = responseBody?.response?.body?.items?.item?.map {
                    it.contentid
                } ?: emptyList()

                onResult(contentIds)
            } else {
                Log.e("TourismAPIvv", "Error: ${response.code()}")
                Log.e("TourismAPIddd", "Response body: ${response.errorBody()?.string()}")
                onResult(emptyList())
            }
        }

        override fun onFailure(call: Call<TripIdData>, t: Throwable) {
            Log.e("TourismAPI", "Failed to get tourism data", t)
            onResult(emptyList())
        }
    })
}

@Composable
fun MainTripTab() {
    var tripIdProperties by remember { mutableStateOf(listOf<TourismItem>()) }

    LaunchedEffect(Unit) {
        val areaCode = 4
        val sigunguCode = 4

        getTripIdData(areaCode, sigunguCode) { properties ->
            tripIdProperties = properties
        }
    }
    var tripContentIds by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        val areaCode = 4
        val sigunguCode = 4

        getTripIdData(areaCode, sigunguCode) { contentIds ->
            tripContentIds = contentIds
        }
    }

    Log.d("TourismData", "$tripContentIds")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "우리고향 관광지",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "전체보기",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(end=8.dp)
        )
    }
}