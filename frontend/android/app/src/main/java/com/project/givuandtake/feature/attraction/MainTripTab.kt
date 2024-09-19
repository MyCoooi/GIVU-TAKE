import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.givuandtake.feature.attraction.FestivalItem
import com.project.givuandtake.feature.attraction.formatDateWithoutYear


// 지역 코드 알아내서 업데이트 필요

// 관광지 데이터를 담을 데이터 클래스 정의
data class TourismItem(
    val trrsrtNm: String,
    val cnvnncFclty: String,
    val rdnmadr: String,
    val trrsrtIntrcn: String,
    val prkplceCo: String
)

// API 응답 데이터의 형식에 맞는 래퍼 클래스 (items가 리스트인 경우)
data class ApiResponse(
    val response: ResponseData
)

data class ResponseData(
    val body: BodyData
)

data class BodyData(
    val items: List<TourismItem>
)

// 관광지 데이터를 가져오는 함수
fun fetchTourismDataWithOkHttp(onDataFetched: (List<TourismItem>) -> Unit) {
    val client = OkHttpClient()
    val url = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api?serviceKey=ClEl7z%2F9nNW%2Fg0NNpuJsf6wBBPJV5UWiVxKC6SzME5GsWrUpQ85zpxv1aJY4Ockw3%2Bm03%2FeCIYyg60sfOqIOxg%3D%3D&pageNo=1&numOfRows=999&type=json&instt_code=5480000"

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OkHttp", "Failed to fetch data", e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    Log.e("OkHttp", "Unexpected code $response")
                    return
                }

                val responseBody = response.body?.string()
                if (responseBody != null) {
                    try {
                        val gson = Gson()
                        // TypeToken을 명시적으로 사용하여 items가 배열로 되어 있음을 정의
                        val apiResponseType = object : TypeToken<ApiResponse>() {}.type
                        val apiResponse: ApiResponse = gson.fromJson(responseBody, apiResponseType)
                        val tourismItems = apiResponse.response.body.items
                        onDataFetched(tourismItems)
                    } catch (e: Exception) {
                        Log.e("OkHttp", "Error parsing JSON", e)
                    }
                }
            }
        }
    })
}

@Composable
fun TripItem(
    location: String,
    description: String,
    title: String,
    facilities: String,
    parking: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = location,
                fontSize = 10.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val periods = facilities.split("+")
                periods.forEach { period ->
                    val displayText = if (period == "주차장") "$period ($parking)" else period
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA093DE), shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = displayText,
                            color = Color.White,
                            fontSize = 8.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainTripTab() {
    var tourismData by remember { mutableStateOf<List<TourismItem>>(emptyList()) }

    // API 호출
    LaunchedEffect(Unit) {
        fetchTourismDataWithOkHttp { data ->
            tourismData = data
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        if (tourismData.isEmpty()) {
            Text(text = "데이터를 불러오는 중입니다...", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Column {
                // 상위 3개 데이터만 출력
                tourismData.forEach { trip ->
                    TripItem(
                        location = trip.rdnmadr ?: "주소 없음",
                        description = trip.trrsrtIntrcn ?: "설명 없음",
                        title = trip.trrsrtNm,
                        facilities = trip.cnvnncFclty,
                        parking = trip.prkplceCo
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
