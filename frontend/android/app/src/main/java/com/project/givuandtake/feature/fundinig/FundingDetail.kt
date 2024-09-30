package com.project.givuandtake.feature.fundinig

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
    var comments by remember { mutableStateOf<List<Comment>?>(null) } // 응원 메시지 리스트

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
                        .aspectRatio(1f)
                        .border(
                            width = 2.dp, // 테두리 두께
                            color = Color.Gray, // 테두리 색상
                            shape = RoundedCornerShape(8.dp) // 테두리 모서리를 둥글게 (선택사항)
                        ),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp) // 전체에 16dp 패딩 추가
                ) {
                    // 위치 및 제목
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn, // 위치 아이콘
                            contentDescription = "위치 아이콘",
                            modifier = Modifier.size(20.dp) // 아이콘 크기 조정
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // 텍스트와 아이콘 간의 간격
                        Text(text = "${detail.sido} ${detail.sigungu}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(text = detail.fundingTitle, style = MaterialTheme.typography.headlineMedium)


                    // 기간 표시
                    Text(text = "${detail.startDate} ~ ${detail.endDate}")


                    // 목표 금액과 모금된 금액
                    val formattedGoalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(detail.goalMoney)
                    val formattedTotalMoney = NumberFormat.getNumberInstance(Locale.KOREA).format(detail.totalMoney)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End // 오른쪽 정렬
                    ) {
                        Text(
                            text = "기부 총액 ",
                            fontSize = 16.sp,
                            color = Color.Black, // 검정색으로 설정
                            modifier = Modifier.alignByBaseline() // 두 텍스트가 같은 선상에 있도록 정렬
                        )
                        Text(
                            text = "${formattedTotalMoney}", // 금액 텍스트
                            fontSize = 20.sp, // 금액을 더 크게
                            color = Color.Blue, // 파란색으로 설정
                            fontWeight = FontWeight.Bold, // Bold로 설정
                            modifier = Modifier.alignByBaseline() // 같은 선상에 정렬
                        )
                        Text(
                            text = "원", // 단위 텍스트
                            fontSize = 16.sp,
                            color = Color.Black, // "원"을 검정색으로 설정
                            modifier = Modifier.alignByBaseline() // 같은 선상에 정렬
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // ProgressBar와 여백 추가

// ProgressBar 및 비율
                    LinearProgressIndicator(
                        progress = if (detail.goalMoney > 0) detail.totalMoney / detail.goalMoney.toFloat() else 0f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp) // 높이를 조정하여 두껍게 설정
                            .clip(RoundedCornerShape(50)), // 모서리를 둥글게 설정
                        color = Color.Blue
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // ProgressBar 아래 여백 추가

                    Text(
                        text = "${(detail.totalMoney / detail.goalMoney.toFloat() * 100).toInt()}%",
                        color = Color.Blue
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Tabs와 여백 추가

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

                    Spacer(modifier = Modifier.height(16.dp)) // 탭 아래 여백 추가
                }

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
