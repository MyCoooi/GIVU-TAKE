package com.project.givuandtake.feature.attraction

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

data class TripIdData(
    val response: ResponseData
)

data class ResponseData(
    val body: BodyData
)

data class BodyData(
    val items: ItemsData
)

data class ItemsData(
    val item: List<TourismItem>
)

data class TourismItem(
    val contentid: String,
    val title: String
)

fun fetchTripIdDataWithOkHttp(areaCode: Int, sigunguCode: Int, onResult: (List<String>) -> Unit) {
    val client = OkHttpClient()
    val serviceKey =
        "ClEl7z%2F9nNW%2Fg0NNpuJsf6wBBPJV5UWiVxKC6SzME5GsWrUpQ85zpxv1aJY4Ockw3%2Bm03%2FeCIYyg60sfOqIOxg%3D%3D"

    // API URL
    val url =
        "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=$serviceKey&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&contentTypeId=12&areaCode=$areaCode&sigunguCode=$sigunguCode"

    // API 요청 생성
    val request = Request.Builder()
        .url(url)
        .build()

    // 비동기 요청 처리
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OkHttp", "Failed to fetch data", e)
            onResult(emptyList()) // 실패 시 빈 리스트 반환
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    Log.e("OkHttp", "Unexpected code $response")
                    onResult(emptyList())
                    return
                }

                // JSON 응답을 로그로 출력
                val responseBody = response.body?.string()
                Log.d("OkHttpResponse", "Response Body: $responseBody")

                if (responseBody != null) {
                    try {
                        // GSON을 사용해서 JSON 데이터를 TripIdData로 변환
                        val gson = Gson()
                        val tripData = gson.fromJson(responseBody, TripIdData::class.java)

                        // contentId만 추출
                        val contentIds = tripData.response.body.items.item.map { it.contentid }
                        onResult(contentIds)
                    } catch (e: Exception) {
                        Log.e("OkHttp", "Error parsing JSON", e)
                        onResult(emptyList())
                    }
                }
            }
        }
    })
}

fun fetchTripDetailsWithOkHttp(contentId: String, onResult: (TourismItemDetails?) -> Unit) {
    val client = OkHttpClient()
    val serviceKey = "ClEl7z%2F9nNW%2Fg0NNpuJsf6wBBPJV5UWiVxKC6SzME5GsWrUpQ85zpxv1aJY4Ockw3%2Bm03%2FeCIYyg60sfOqIOxg%3D%3D"

    // API URL
    val url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?serviceKey=$serviceKey&MobileOS=ETC&MobileApp=AppTest&_type=json&contentId=$contentId&contentTypeId=12&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&numOfRows=10&pageNo=1"

    // API 요청 생성
    val request = Request.Builder()
        .url(url)
        .build()

    // 비동기 요청 처리
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OkHttp", "Failed to fetch detailed data", e)
            onResult(null) // 실패 시 null 반환
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    Log.e("OkHttp", "Unexpected code $response")
                    onResult(null)
                    return
                }

                val responseBody = response.body?.string()
                Log.d("OkHttpResponse", "Response Body: $responseBody")

                if (responseBody != null) {
                    try {
                        val gson = Gson()
                        val tripDetailData = gson.fromJson(responseBody, TripDetailResponse::class.java)
                        val item = tripDetailData.response.body.items.item.firstOrNull()
                        onResult(item) // 성공 시 첫 번째 item 반환
                    } catch (e: Exception) {
                        Log.e("OkHttp", "Error parsing JSON", e)
                        onResult(null)
                    }
                }
            }
        }
    })
}

// Define the data model for the response
data class TripDetailResponse(
    val response: TripDetailResponseBody
)

data class TripDetailResponseBody(
    val body: TripDetailResponseItems
)

data class TripDetailResponseItems(
    val items: TripDetailItems
)

data class TripDetailItems(
    val item: List<TourismItemDetails>
)

data class TourismItemDetails(
    val contentid: String,
    val title: String,
    val firstimage: String?,
    val addr1: String,
    val addr2: String?,
    val mapx: String,
    val mapy: String,
    val overview: String
)

@Composable
fun TripPage(navController: NavController, city: String?) {
    var tripContentIds by remember { mutableStateOf(listOf<String>()) }
    var tripMainData by remember { mutableStateOf(listOf<TourismItemDetails>()) }

    LaunchedEffect(Unit) {
        val (areaCode, sigunguCode) = when (city) {
            "영도" -> Pair(6, 14) // 부산 영도구
            "군위" -> Pair(4, 9) // 대구 군위군

            "남원" -> Pair(37, 4) // 전북 남원시
            "무주" -> Pair(37, 5) // 전북 무주군
            "순창" -> Pair(37, 7) // 전북 순창군
            "임실" -> Pair(37, 10) // 전북 임실군

            "고흥" -> Pair(38, 2) // 전남 고흥군
            "보성" -> Pair(38, 10) // 전남 보성군
            "신안" -> Pair(38, 12) // 전남 신안군
            "함평" -> Pair(38, 22) // 전남 함평군

            "고성" -> Pair(36, 3) // 경남 고성군
            "남해" -> Pair(36, 5) // 경남 남해군
            "하동" -> Pair(36, 18) // 경남 하동군
            "합천" -> Pair(36, 21) // 경남 합천군

            "문경" -> Pair(35, 7) // 경북 문경시
            "상주" -> Pair(35, 9) // 경북 상주시
            "안동" -> Pair(35, 11) // 경북 안동시
            "영천" -> Pair(35, 15) // 경북 영천시

            "평창" -> Pair(32, 15) // 강원 평창군
            "횡성" -> Pair(32, 18) // 강원 횡성군
            "태백" -> Pair(32, 14) // 강원 태백시
            "정선" -> Pair(32, 11) // 강원 정선군

            "괴산" -> Pair(33, 1) // 충북 괴산군
            "보은" -> Pair(33, 3) // 충북 보은군
            "영동" -> Pair(33, 4) // 충북 영동군
            "제천" -> Pair(33, 7) // 충북 제천시

            "보령" -> Pair(34, 5) // 충남 보령시
            "부여" -> Pair(34, 6) // 충남 부여군
            "공주" -> Pair(34, 1) // 충남 공주시
            "태안" -> Pair(34, 14) // 충남 태안군

            else -> Pair(34, 14) // Default to Seoul if no match
        }

        fetchTripIdDataWithOkHttp(areaCode, sigunguCode) { contentIds ->
            // For each contentId, fetch the detailed data
            contentIds.forEach { contentId ->
                fetchTripDetailsWithOkHttp(contentId) { detail ->
                    detail?.let {
                        // Add the detail to the list
                        tripMainData = tripMainData + it
                    }
                }
            }
        }
    }

    Log.d("TourismData", "$tripContentIds")
    Log.d("TourismData", "$tripMainData")

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    var mapIsReady by remember { mutableStateOf(false) }

    val (lat, lon) = when (city) {
        "영도" -> Pair(35.0911, 129.0689) // 부산 영도구
        "군위" -> Pair(36.2395, 128.5727) // 대구 군위군

        "남원" -> Pair(35.4164, 127.3900) // 전북 남원시
        "무주" -> Pair(35.9078, 127.6606) // 전북 무주군
        "순창" -> Pair(35.3741, 127.1387) // 전북 순창군
        "임실" -> Pair(35.6175, 127.2886) // 전북 임실군

        "고흥" -> Pair(34.6050, 127.2827) // 전남 고흥군
        "보성" -> Pair(34.7717, 127.0802) // 전남 보성군
        "신안" -> Pair(34.8277, 126.1072) // 전남 신안군
        "함평" -> Pair(35.0650, 126.5169) // 전남 함평군

        "고성" -> Pair(34.9733, 128.3236) // 경남 고성군
        "남해" -> Pair(34.8371, 127.8925) // 경남 남해군
        "하동" -> Pair(35.0666, 127.7514) // 경남 하동군
        "합천" -> Pair(35.5661, 128.1654) // 경남 합천군

        "문경" -> Pair(36.5866, 128.1996) // 경북 문경시
        "상주" -> Pair(36.4106, 128.1593) // 경북 상주시
        "안동" -> Pair(36.5684, 128.7227) // 경북 안동시
        "영천" -> Pair(35.9733, 128.9389) // 경북 영천시

        "평창" -> Pair(37.3704, 128.3906) // 강원 평창군
        "횡성" -> Pair(37.4912, 127.9846) // 강원 횡성군
        "태백" -> Pair(37.1640, 128.9859) // 강원 태백시
        "정선" -> Pair(37.3800, 128.6608) // 강원 정선군

        "괴산" -> Pair(36.8152, 127.7902) // 충북 괴산군
        "보은" -> Pair(36.4897, 127.7297) // 충북 보은군
        "영동" -> Pair(36.1750, 127.7766) // 충북 영동군
        "제천" -> Pair(37.1325, 128.1900) // 충북 제천시

        "보령" -> Pair(36.3335, 126.6129) // 충남 보령시
        "부여" -> Pair(36.2744, 126.9094) // 충남 부여군
        "공주" -> Pair(36.4467, 127.1192) // 충남 공주시
        "태안" -> Pair(36.7456, 126.2970) // 충남 태안군

        else -> Pair(37.5665, 126.9780) // Default to Seoul if no match
    }

    val options = NaverMapOptions()
        .camera(CameraPosition(LatLng(lat, lon), 11.0))

    val mapView = remember {
        MapView(context, options).apply {
            getMapAsync { naverMap ->
                // Update mapIsReady once the map is initialized
                mapIsReady = true
            }
        }
    }

    // Lifecycle 이벤트를 수신하기 위해 AndroidView의 밖에서 먼저 선언합니다.
    // recomposition시에도 유지되어야 하기 때문에 remember { } 로 기억합니다.
    LaunchedEffect(tripMainData, mapIsReady) {
        if (mapIsReady && tripMainData.isNotEmpty()) {
            mapView.getMapAsync { naverMap ->
                tripMainData.forEach { item ->
                    val marker = Marker()
                    marker.position = LatLng(item.mapy.toDouble(), item.mapx.toDouble()) // Lat and Lng
                    marker.captionText = item.title
                    marker.icon = MarkerIcons.RED
//                    marker.width = Marker.SIZE_AUTO
                    marker.map = naverMap
                }
            }
        }
    }

    // LifecycleEventObserver를 구현하고, 각 이벤트에 맞게 MapView의 Lifecycle 메소드를 호출합니다.
    val lifecycleObserver = remember {
        LifecycleEventObserver { source, event ->
            // CoroutineScope 안에서 호출해야 정상적으로 동작합니다.
            coroutineScope.launch {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> Unit
                }
            }
        }
    }

    // 뷰가 해제될 때 이벤트 리스너를 제거합니다.
    DisposableEffect(true) {
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(25.dp)
                .size(55.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .clickable { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .heightIn(min = 180.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
                .padding(20.dp)
        ) {
            val displayedText = when (city) {
                "영도" -> "부산광역시 영도구"
                "군위" -> "대구광역시 군위군"

                "남원" -> "전북특별자치도 남원시"
                "무주" -> "전북특별자치도 무주군"
                "순창" -> "전북특별자치도 순창군"
                "임실" -> "전북특별자치도 임실군"

                "고흥" -> "전라남도 고흥군"
                "보성" -> "전라남도 보성군"
                "신안" -> "전라남도 신안군"
                "함평" -> "전라남도 함평군"

                "고성" -> "경상남도 고성군"
                "남해" -> "경상남도 남해군"
                "하동" -> "경상남도 하동군"
                "합천" -> "경상남도 합천군"

                "문경" -> "경상북도 문경시"
                "상주" -> "경상북도 상주시"
                "안동" -> "경상북도 안동시"
                "영천" -> "경상북도 영천시"

                "평창" -> "강원특별자치도 평창군"
                "횡성" -> "강원특별자치도 횡성군"
                "태백" -> "강원특별자치도 태백시"
                "정선" -> "강원특별자치도 정선군"

                "괴산" -> "충청북도 괴산군"
                "보은" -> "충청북도 보은군"
                "영동" -> "충청북도 영동군"
                "제천" -> "충청북도 제천시"

                "보령" -> "충청남도 보령시"
                "부여" -> "충청남도 부여군"
                "공주" -> "충청남도 공주시"
                "태안" -> "충청남도 태안군"
                else -> city // Default case for cities not specified
            }
            Text(
                text = "$displayedText",
                fontSize = 25.sp, // Set the font size (e.g., 20sp)
                fontWeight = FontWeight.Bold, // Make the text bold
                modifier = Modifier.padding(8.dp) // Optional: Add some padding if needed
            )
        }
    }
}
