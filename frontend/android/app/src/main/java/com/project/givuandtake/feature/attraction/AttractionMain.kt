import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R
import com.skydoves.landscapist.glide.GlideImage
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import coil.compose.rememberImagePainter
import com.project.givuandtake.core.apis.RetrofitInstance
import com.project.givuandtake.core.data.WeatherData
import com.project.givuandtake.feature.attraction.MainMarketTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getWeatherData(lat: Double, lon: Double, onResult: (String, String, String) -> Unit) {
    val apiKey = "fe4c6b378cbe4af2538f2d255f5bdcea" // API 키를 여기에 입력
    val lang = "kr"

    RetrofitInstance.api.getWeather(lat, lon, apiKey, lang).enqueue(object : Callback<WeatherData> {
        override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
            if (response.isSuccessful) {
                val weatherData = response.body()
                val temperature = weatherData?.main?.temp?.minus(273.15) // 켈빈에서 섭씨로 변환
                val weatherDes = weatherData?.weather?.get(0)?.main
                val weatherMoreDes = weatherData?.weather?.get(0)?.description
                onResult(temperature?.toInt().toString(), weatherDes ?: "", weatherMoreDes ?: "")
            } else {
                Log.e("Weather", "Error: ${response.code()}")
                onResult("", "", "")
            }
        }

        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
            Log.e("Weather", "Failed to get weather data", t)
            onResult("", "", "")
        }
    })
}
@Composable
fun GifImage(weatherDes: String) {
    // weatherMain 값을 기반으로 이미지 파일 경로 설정
    val assetPath = when (weatherDes) {
        "Clear" -> R.drawable.clear
        "Clouds" -> R.drawable.clouds
        "Atmosphere" -> R.drawable.atmosphere
        "Snow" -> R.drawable.snow
        "Rain" -> R.drawable.rain
        "Drizzle" -> R.drawable.drizzle
        "Thunderstorm" -> R.drawable.thunderstrom
        else -> R.drawable.clear // 기본값
    }

    // 이미지 로딩
    GlideImage(
        imageModel = assetPath,
        modifier = Modifier.size(90.dp).clip(RoundedCornerShape(30.dp))
    )
}


@Composable
fun MarketItem(
    marketName: String,
    address: String,
    parkingAvailable: Boolean,
    restroomAvailable: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 4.dp, // 그림자 크기 조정
                shape = RoundedCornerShape(12.dp),
                clip = false, // 박스의 하단만 그림자가 나오도록 설정
                ambientColor = Color.LightGray // 은은한 그림자 색상 설정
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp)) // 박스 배경
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = address, fontSize = 18.sp, color = Color.Gray) // 추가된 주소 텍스트
                Text(text = marketName, fontSize = 26.sp, color = Color.Black)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // 가운데 정렬
                verticalArrangement = Arrangement.Center
            ) {
                if (parkingAvailable) {
                    Image(
                        painter = painterResource(id = R.drawable.parking),  // parking.png 사용
                        contentDescription = "Parking available",
                        modifier = Modifier.size(24.dp) // 아이콘 크기 조정
                    )
                    Spacer(modifier = Modifier.height(4.dp)) // 위아래 간격 추가
                }
                if (restroomAvailable) {
                    Image(
                        painter = painterResource(id = R.drawable.toilet),  // toilet.png 사용
                        contentDescription = "Restroom available",
                        modifier = Modifier.size(24.dp) // 아이콘 크기 조정
                    )
                }
            }
        }
    }
}


@Composable
fun SquareMarketItem(
    marketName: String,
    address: String,
    parkingAvailable: Boolean,
    restroomAvailable: Boolean
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 주소 텍스트 (상단)
            Text(
                text = address,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = marketName,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // 주차장 및 화장실 아이콘 (중간)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                if (parkingAvailable) {
                    Image(
                        painter = painterResource(id = R.drawable.parking),
                        contentDescription = "Parking available",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp)) // 아이콘 간 간격
                }
                if (restroomAvailable) {
                    Image(
                        painter = painterResource(id = R.drawable.toilet),
                        contentDescription = "Restroom available",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AttractionMain(navController: NavController) {
    val scrollState = rememberScrollState()

    var province by remember { mutableStateOf("도 선택") }
    var state by remember { mutableStateOf("구 선택") }
    var availableStates by remember { mutableStateOf(listOf<String>()) }
    var selectedLocation by remember { mutableStateOf("") }
    var expandedProvince by remember { mutableStateOf(false) }
    var expandedState by remember { mutableStateOf(false) }

    var temperature by remember { mutableStateOf("") }
    var weatherDes by remember { mutableStateOf("") }
    var weatherMoreDes by remember { mutableStateOf("") }

    // 탭 상태 관리
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("전통시장", "축제", "관광지", "체험마을")

    LaunchedEffect(Unit) {
        // 서울 좌표를 사용한 예시 (실제로는 선택된 위치에 따라 다르게 설정 가능)
        val lat = 37.5665
        val lon = 126.9780

        // 비동기 API 호출로 날씨 데이터를 가져옴
        getWeatherData(lat, lon) { temp, weather, des ->
            temperature = temp // 상태 업데이트
            weatherDes = weather
            weatherMoreDes = des
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFB3C3F4)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 드롭다운 메뉴
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB3C3F4), shape = RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 도 선택 버튼
                Box(modifier = Modifier.wrapContentSize()) {
                    OutlinedButton(
                        onClick = { expandedProvince = !expandedProvince },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFFFFFFF), // 원하는 배경색
                            contentColor = Color.Black // 원하는 텍스트 색
                        ),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = province)
                    }
                    DropdownMenu(
                        expanded = expandedProvince,
                        onDismissRequest = { expandedProvince = false }
                    ) {
                        listOf("부산광역시", "서울특별시", "대구광역시").forEach { selectedProvince ->
                            DropdownMenuItem(onClick = {
                                province = selectedProvince
                                expandedProvince = false
                                availableStates = when (selectedProvince) {
                                    "부산광역시" -> listOf("영도구")
                                    "서울특별시" -> listOf("강남구")
                                    "대구광역시" -> listOf("수성구")
                                    else -> listOf()
                                }
                                state = "구 선택"
                                selectedLocation = ""
                            }) {
                                Text(text = selectedProvince)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 구 선택 버튼
                Box(modifier = Modifier.wrapContentSize()) {
                    OutlinedButton(
                        onClick = { expandedState = !expandedState },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFFFFFFF), // 원하는 배경색
                            contentColor = Color.Black // 원하는 텍스트 색
                        ),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = state)
                    }
                    DropdownMenu(
                        expanded = expandedState,
                        onDismissRequest = { expandedState = false }
                    ) {
                        availableStates.forEach { selectedState ->
                            DropdownMenuItem(onClick = {
                                state = selectedState
                                expandedState = false
                                if (province != "도 선택" && state != "구 선택") {
                                    selectedLocation = "$province $state"
                                }
                            }) {
                                Text(text = selectedState)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 날씨 정보와 탭 UI 추가
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(110.dp)
                .background(Color(0xFF4099E9), shape = RoundedCornerShape(30.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "Location Icon",
                            modifier = Modifier.size(24.dp) // 이미지 크기 설정
                        )
                        Text(
                            text = " 부산광역시 영도구",
                            color = Color(0xFFFFFFFF)
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            append(temperature)
                            withStyle(style = SpanStyle(fontSize = 20.sp)) {
                                append("°C   ")
                            }
                            withStyle(style = SpanStyle(fontSize = 25.sp)) {
                                append("$weatherMoreDes")
                            }
                        },
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                    )
                }
                GifImage(weatherDes)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(0.9f), // 전체 너비 차지
            horizontalArrangement = Arrangement.Center, // 탭 간 간격 조절
            verticalAlignment = Alignment.CenterVertically

        ) {
            items(tabs.size) { index ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.wrapContentWidth().padding(horizontal = 12.dp) // 탭 너비를 텍스트에 맞춤
                ) {
                    Text(
                        text = tabs[index],
                        color = if (selectedTabIndex == index) Color.Black else Color.White, // 선택 여부에 따라 글씨 색상 변경
                        fontSize = 20.sp,
                        style = androidx.compose.ui.text.TextStyle(
                            textDecoration = if (selectedTabIndex == index) {
                                TextDecoration.Underline // 선택된 탭에 밑줄 적용
                            } else {
                                TextDecoration.None // 선택되지 않은 탭은 밑줄 없음
                            }
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 탭과 모든 콘텐츠를 감싸는 박스
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 24.dp, // 왼쪽 상단만 둥글게
                        topEnd = 24.dp,   // 오른쪽 상단만 둥글게
                        bottomEnd = 0.dp, // 하단은 그대로
                        bottomStart = 0.dp // 하단은 그대로
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                // 탭에 따른 내용
                when (selectedTabIndex) {
                    // 전통시장 탭이 선택된 경우에만 내용이 나타남
                    0 -> {
                        MainMarketTab()
                    }
                    // 다른 탭이 선택된 경우 빈 상태로 둠
                    else -> {
                        Text("", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
