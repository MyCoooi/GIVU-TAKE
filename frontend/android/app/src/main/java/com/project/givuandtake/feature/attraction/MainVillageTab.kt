package com.project.givuandtake.feature.attraction

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.givuandtake.core.apis.Viliage.ViliageApi
import com.project.givuandtake.core.data.Viliage.ExperienceVillage
import com.project.givuandtake.core.data.Viliage.VillageData
import com.project.givuandtake.core.datastore.TokenManager
import com.project.givuandtake.feature.mypage.MyActivities.AddressViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainVillageViewModel : ViewModel() {

    private val _villageData = mutableStateOf<List<ExperienceVillage>>(emptyList())
    val villageData: State<List<ExperienceVillage>> = _villageData

    fun fetchVillageData(sido: String, sigungu: String, division: String?, pageNo: Int?, pageSize: Int? ) {
        viewModelScope.launch {
            try {
                val response: Response<VillageData> = ViliageApi.api.getExperienceVillage(sido, sigungu)
                if (response.isSuccessful) {
                    Log.d("MainVillageViewModel", "체험 마을 데이터: ${response.body()?.data}")
                    response.body()?.let {
                        Log.d("MainVillageViewModel", "체험 마을 데이터 가져오기 성공: ${it.data}")
                        _villageData.value = it.data
                    } ?: run {
                        Log.e("MainVillageViewModel", "응답은 성공했으나 데이터가 비어있습니다.")
                    }
                } else {
                    Log.e("MainVillageViewModel", "체험 마을 데이터 가져오기 실패: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainVillageViewModel", "API 호출 중 예외 발생: ${e.message}", e)
            }
        }
    }
}

@Composable
fun MainVillageTab(
    navController: NavController,
    displayedCity: String,
) {
    val viewModel: MainVillageViewModel = viewModel()

    val (sido, sigungu) = when (displayedCity) {
        "영도" -> Pair("부산광역시", "영도구")
        "군위" -> Pair("대구광역시", "군위군")

        "남원" -> Pair("전북특별자치도", "남원시")
        "무주" -> Pair("전북특별자치도", "무주군")
        "순창" -> Pair("전북특별자치도", "순창군")
        "임실" -> Pair("전북특별자치도", "임실군")

        "고흥" -> Pair("전라남도", "고흥군")
        "보성" -> Pair("전라남도", "보성군")
        "신안" -> Pair("전라남도", "신안군")
        "함평" -> Pair("전라남도", "함평군")

        "고성" -> Pair("경상남도", "고성군")
        "남해" -> Pair("경상남도", "남해군")
        "하동" -> Pair("경상남도", "하동군")
        "합천" -> Pair("경상남도", "합천군")

        "문경" -> Pair("경상북도", "문경시")
        "상주" -> Pair("경상북도", "상주시")
        "안동" -> Pair("경상북도", "안동시")
        "영천" -> Pair("경상북도", "영천시")

        "평창" -> Pair("강원특별자치도", "평창군")
        "횡성" -> Pair("강원특별자치도", "횡성군")
        "태백" -> Pair("강원특별자치도", "태백시")
        "정선" -> Pair("강원특별자치도", "정선군")

        "괴산" -> Pair("충청북도", "괴산군")
        "보은" -> Pair("충청북도", "보은군")
        "영동" -> Pair("충청북도", "영동군")
        "제천" -> Pair("충청북도", "제천시")

        "보령" -> Pair("충청남도", "보령시")
        "부여" -> Pair("충청남도", "부여군")
        "공주" -> Pair("충청남도", "공주시")
        "태안" -> Pair("충청남도", "태안군")

        else -> Pair("기타", "기타")
    }

    LaunchedEffect(Unit) {
        viewModel.fetchVillageData(
            sido = sido,
            sigungu = sigungu,
            division = null,
            pageNo = null,
            pageSize = null
        )
    }

    val villageData by viewModel.villageData
    Log.d("12341234", "$villageData")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "우리 고향 체험마을",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
            Text(
                text = "전체보기",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clickable {
                        val city = displayedCity
                        navController.navigate("viliagepage?city=$city")
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (villageData.isEmpty()) {
            Text(text = "등록된 체험 마을이 없습니다.", modifier = Modifier.padding(16.dp))
        } else {
            Log.d("123456", "123456")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // 첫 3개의 항목만 보여줌
                villageData.take(3).forEach { village ->
                    village?.let {
                        Log.d("MainVillageTab", "village: ${village.experienceVillageName}")
                        VillageItem(it)
                    } ?: run {
                        Log.e("MainVillageTab", "null 데이터가 전달되었습니다.")
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // 각 항목 사이에 간격 추가
                }
            }
        }
    }
}

@Composable
fun VillageItem(village: ExperienceVillage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(text = village.experienceVillageAddress, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = village.experienceVillageName, fontSize = 25.sp)
    }
}

