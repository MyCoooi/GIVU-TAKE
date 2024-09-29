package com.project.givuandtake.feature.fundinig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.givuandtake.core.apis.Funding.FundingDetailApi
import com.project.givuandtake.core.apis.Funding.FundingDetailData
import com.project.givuandtake.core.apis.Funding.FundingDetailResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundingDetailPage(
    fundingIdx: Int,
    navController: NavController,
    onBackClick: () -> Unit
) {
    var fundingDetail by remember { mutableStateOf<FundingDetailData?>(null) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val tabs = listOf("사업소개", "응원메시지", "기부후기")

    // 펀딩 상세 데이터 로드
    LaunchedEffect(fundingIdx) {
        fetchFundingDetail(fundingIdx) { detail ->
            fundingDetail = detail
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("펀딩 상세보기") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("payment") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "기부하기", color = Color.White)
            }
        }
    ) { innerPadding ->
        fundingDetail?.let { detail ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                // 이미지 표시
                AsyncImage(
                    model = detail.fundingThumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 위치 및 제목
                Text(text = "${detail.sido} ${detail.sigungu}", style = MaterialTheme.typography.bodyMedium)
                Text(text = detail.fundingTitle, style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // 기간 표시
                Text(text = "${detail.startDate} ~ ${detail.endDate}")

                Spacer(modifier = Modifier.height(8.dp))

                // 목표 금액과 모금된 금액
                val formattedGoalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(detail.goalMoney)
                val formattedTotalMoney = NumberFormat.getNumberInstance(Locale.KOREA).format(detail.totalMoney)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "기부 총액", fontSize = 16.sp)
                    Text(text = "${formattedTotalMoney}원 / ${formattedGoalAmount}원", fontSize = 16.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ProgressBar 및 비율
                LinearProgressIndicator(
                    progress = if (detail.goalMoney > 0) detail.totalMoney / detail.goalMoney.toFloat() else 0f,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${(detail.totalMoney / detail.goalMoney.toFloat() * 100).toInt()}%")

                Spacer(modifier = Modifier.height(16.dp))

                // Tabs (사업소개, 응원메시지, 기부후기)
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, tabTitle ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                scope.launch {
                                    selectedTabIndex = index
                                    if (index == 1) {
                                        // 응원메시지 탭으로 이동하면 스크롤 위치를 응원메시지로 이동
                                        scrollState.animateScrollTo(scrollState.maxValue) // 응원메시지 위치로 스크롤
                                    }
                                }
                            },
                            text = { Text(tabTitle) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 탭에 따라 다른 콘텐츠 표시
                when (selectedTabIndex) {
                    0 -> {
                        // 사업소개
                        Text(text = detail.fundingContent)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    1 -> {
                        // 응원메시지 (임시 데이터)
                        Text(text = "응원메시지들: 아직 추가되지 않았습니다.")
                    }
                    2 -> Text(text = "기부후기: 아직 추가되지 않았습니다.")
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        } ?: run {
            // 데이터가 없을 때 로딩 메시지
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text("데이터를 불러오는 중입니다...")
            }
        }
    }
}

// 펀딩 상세 데이터 가져오기
fun fetchFundingDetail(fundingIdx: Int, onSuccess: (FundingDetailData) -> Unit) {
    val call = FundingDetailApi.api.getFundingDetail(fundingIdx)
    call.enqueue(object : Callback<FundingDetailResponse> {  // Callback<FundingDetailResponse>로 수정
        override fun onResponse(call: Call<FundingDetailResponse>, response: Response<FundingDetailResponse>) {
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { onSuccess(it) }  // response.body()?.data 사용
            }
        }

        override fun onFailure(call: Call<FundingDetailResponse>, t: Throwable) {
            // 에러 처리
        }
    })
}
