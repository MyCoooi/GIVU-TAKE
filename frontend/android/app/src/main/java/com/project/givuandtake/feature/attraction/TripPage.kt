package com.project.givuandtake.feature.attraction

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.naver.maps.map.MapView
import kotlinx.coroutines.launch

//import android.R
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavController

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
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.fragment.app.FragmentActivity
//import androidx.fragment.app.commit
//import androidx.navigation.NavController
//import com.naver.maps.map.MapFragment
//import androidx.fragment.app.FragmentContainerView
//import com.project.yourapp.R
//
//@Composable
//fun TripPage(navController: NavController) {
//    Text("sdfg")

//    AndroidView(
//        factory = { context ->
//            FragmentContainerView(context).apply {
//                id = R.id.map_fragment // ID 설정
//                (context as FragmentActivity).supportFragmentManager.commit {
//                    // Naver MapFragment를 추가
//                    replace(R.id.map_fragment, MapFragment.newInstance())
//                }
//            }
//        },
//        modifier = Modifier
//            .fillMaxSize() // 전체 화면 크기 설정
//    )
//}

//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.fragment.app.FragmentActivity
//import androidx.fragment.app.FragmentContainerView
//import androidx.fragment.app.FragmentTransaction
//import androidx.navigation.NavController
//import com.naver.maps.map.MapFragment
//import com.project.givuandtake.R
//
//@Composable
//fun TripPage(navController: NavController) {
//    // AndroidView를 사용하여 Compose 안에서 Naver Map을 표시
//    AndroidView(
//        factory = { context ->
//            // FragmentContainerView 생성
//            FragmentContainerView(context).apply {
//                id = R.id.map_fragment // ID 설정
//
//                // FragmentManager를 통해 MapFragment 추가
//                val fragmentManager = (context as FragmentActivity).supportFragmentManager
//                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//                val mapFragment = MapFragment.newInstance()
//
//                // MapFragment를 FragmentContainerView에 추가
//                fragmentTransaction.replace(R.id.map_fragment, mapFragment)
//                fragmentTransaction.commit()
//            }
//        },
//        modifier = Modifier
//            .fillMaxSize() // 전체 화면 크기로 설정
//    )
//}

@Composable
fun TripPage(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    // Lifecycle 이벤트를 수신하기 위해 AndroidView의 밖에서 먼저 선언합니다.
    // recomposition시에도 유지되어야 하기 때문에 remember { } 로 기억합니다.
    val mapView = remember {
        MapView(context).apply {
            getMapAsync { naverMap ->
//                ... 초기 설정 ...
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

    // 생성된 MapView 객체를 AndroidView로 Wrapping 합니다.
    AndroidView(factory = { mapView })
}
