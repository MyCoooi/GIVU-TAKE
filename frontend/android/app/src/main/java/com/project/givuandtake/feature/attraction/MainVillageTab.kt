package com.project.givuandtake.feature.attraction

import android.util.Log
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.givuandtake.core.apis.UserInfoResponse
import com.project.givuandtake.core.apis.ViliageApi
import com.project.givuandtake.core.data.ExperienceVillage
import com.project.givuandtake.core.data.VillageData
import com.project.givuandtake.core.datastore.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Response

class MainVillageViewModel : ViewModel() {

    private val _villageData = mutableStateOf<List<ExperienceVillage>>(emptyList())
    val villageData: State<List<ExperienceVillage>> = _villageData

    init {
        fetchVillageData("부산광역시", "영도구", "", 0, 0)
    }

    private fun fetchVillageData(sido: String, sigungu: String, division: String, pageNo: Int, pageSize: Int) {
        viewModelScope.launch {
            val response: Response<VillageData> = ViliageApi.api.getExperienceVillage(sido, sigungu, division, pageNo, pageSize)
            if (response.isSuccessful) {
                response.body()?.let {
                    _villageData.value = it.data // 성공적으로 가져온 체험 마을 데이터를 저장
                }
            }
        }
    }
}

@Composable
fun MainVillageTab(
    navController: NavController,
    displayedCity: String,
    viewModel: MainVillageViewModel
) {
    val context = LocalContext.current
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"
    val villageData by viewModel.villageData

    Log.d("asdfasdf", "$accessToken")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "우리고향 체험마을",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "전체보기",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(villageData) { village ->
                VillageItem(village)
            }
        }
    }
}

@Composable
fun VillageItem(village: ExperienceVillage) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Text(text = village.experienceVillageName, fontSize = 18.sp)
        Text(text = village.experienceVillageAddress, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
    }
}