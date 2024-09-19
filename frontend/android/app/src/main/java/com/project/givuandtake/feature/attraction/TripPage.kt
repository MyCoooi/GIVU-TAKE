package com.project.givuandtake.feature.attraction

import android.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView


//data class TripIdData(
//    val response: ResponseData
//)
//
//data class ResponseData(
//    val body: BodyData
//)
//
//data class BodyData(
//    val items: ItemsData
//)
//
//data class ItemsData(
//    val item: List<TourismItem>
//)
//
//data class TourismItem(
//    val contentid: String,
//    val title: String
//)
//
//fun fetchTripIdDataWithOkHttp(areaCode: Int, sigunguCode: Int, onResult: (List<String>) -> Unit) {
//    val client = OkHttpClient()
//    val serviceKey =
//        "ClEl7z%2F9nNW%2Fg0NNpuJsf6wBBPJV5UWiVxKC6SzME5GsWrUpQ85zpxv1aJY4Ockw3%2Bm03%2FeCIYyg60sfOqIOxg%3D%3D"
//
//    // API URL
//    val url =
//        "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=$serviceKey&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&contentTypeId=12&areaCode=$areaCode&sigunguCode=$sigunguCode"
//
//    // API 요청 생성
//    val request = Request.Builder()
//        .url(url)
//        .build()
//
//    // 비동기 요청 처리
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            Log.e("OkHttp", "Failed to fetch data", e)
//            onResult(emptyList()) // 실패 시 빈 리스트 반환
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            response.use {
//                if (!response.isSuccessful) {
//                    Log.e("OkHttp", "Unexpected code $response")
//                    onResult(emptyList())
//                    return
//                }
//
//                // JSON 응답을 로그로 출력
//                val responseBody = response.body?.string()
//                Log.d("OkHttpResponse", "Response Body: $responseBody")
//
//                if (responseBody != null) {
//                    try {
//                        // GSON을 사용해서 JSON 데이터를 TripIdData로 변환
//                        val gson = Gson()
//                        val tripData = gson.fromJson(responseBody, TripIdData::class.java)
//
//                        // contentId만 추출
//                        val contentIds = tripData.response.body.items.item.map { it.contentid }
//                        onResult(contentIds)
//                    } catch (e: Exception) {
//                        Log.e("OkHttp", "Error parsing JSON", e)
//                        onResult(emptyList())
//                    }
//                }
//            }
//        }
//    })
//}

//    var tripMainData by remember { mutableStateOf(listOf<TourismItem>()) }
//    var tripContentIds by remember { mutableStateOf(listOf<String>()) }

//    LaunchedEffect(Unit) {
//        val areaCode = 4
//        val sigunguCode = 4
//
//        fetchTripIdDataWithOkHttp(areaCode, sigunguCode) { contentIds ->
//            tripContentIds = contentIds
//        }
//    }

//    Log.d("TourismData", "$tripContentIds")

// 관광지 데이터를 보여주는 부분
//    tripContentIds.forEach { contentId ->
//        Text(
//            text = "Content ID: $contentId",
//            modifier = Modifier.padding(8.dp)
//        )
//    }
@Composable
fun TripPage(navController: NavController) {
    val context = LocalContext.current

    // AndroidView를 사용하여 MapView 표시
    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                // KakaoMap의 라이프사이클 콜백 설정
                start(object : MapLifeCycleCallback() {
                    override fun onMapDestroy() {
                        Log.d("KakaoMap", "onMapDestroy")
                    }

                    override fun onMapError(p0: Exception?) {
                        Log.e("KakaoMap", "onMapError", p0)
                    }
                }, object : KakaoMapReadyCallback() {
                    override fun onMapReady(kakaoMap: KakaoMap) {
                        // KakaoMap이 준비되었을 때 필요한 동작 추가
                        Log.d("KakaoMap", "Map is ready")
                    }
                })
            }
        },
        modifier = Modifier.fillMaxSize(), // 화면 전체에 지도 표시
        update = { mapView ->
            // 필요 시 업데이트 작업 수행
        }
    )
}
