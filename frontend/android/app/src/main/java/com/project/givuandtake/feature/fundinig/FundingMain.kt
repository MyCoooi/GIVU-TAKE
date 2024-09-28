package com.project.givuandtake.feature.fundinig

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.project.givuandtake.core.apis.FundingData
import com.project.givuandtake.core.apis.FundingResponse
import com.project.givuandtake.core.apis.SearchFundingApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun FundingMainPage(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // 상태 관리 변수
    var disasterCards by remember { mutableStateOf<List<FundingData>>(emptyList()) }
    var donationCards by remember { mutableStateOf<List<FundingData>>(emptyList()) }
    var disasterState by remember { mutableStateOf(1) }  // 재난·재해 상태 (진행 중: 1, 완료: 2)
    var donationState by remember { mutableStateOf(1) }  // 지역 기부 상태 (진행 중: 1, 완료: 2)

    // 드롭다운 상태
    var disasterExpanded by remember { mutableStateOf(false) }
    var donationExpanded by remember { mutableStateOf(false) }
    var disasterSelectedSort by remember { mutableStateOf("종료 임박순") }
    var donationSelectedSort by remember { mutableStateOf("종료 임박순") }

    // 재난·재해 펀딩 데이터 로드
    LaunchedEffect(disasterState) {
        fetchFundingData("D", disasterState) { result -> disasterCards = result }
    }

    // 지역 기부 펀딩 데이터 로드
    LaunchedEffect(donationState) {
        fetchFundingData("R", donationState) { result -> donationCards = result }
    }

    // UI 구성
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // 재난·재해 섹션
        Text(text = "재난·재해", style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically  // 라디오 버튼과 텍스트 정렬
            ) {
                RadioButton(
                    selected = disasterState == 1,
                    onClick = { disasterState = 1 }
                )
                Text(text = "진행 중")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = disasterState == 2,
                    onClick = { disasterState = 2 }
                )
                Text(text = "완료")
            }

            // 재난·재해 드롭다운 메뉴
            Box {
                TextButton(onClick = { disasterExpanded = true }) {
                    Text(text = disasterSelectedSort)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = disasterExpanded,
                    onDismissRequest = { disasterExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("종료 임박순") },
                        onClick = {
                            disasterSelectedSort = "종료 임박순"
                            disasterExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("최근 등록순") },
                        onClick = {
                            disasterSelectedSort = "최근 등록순"
                            disasterExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 재난·재해 리스트
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(300.dp)) {
            items(disasterCards) { card ->
                FundingCardComposable(
                    title = card.fundingTitle,
                    location = "${card.sido} ${card.sigungu}",
                    startDate = card.startDate,
                    endDate = card.endDate,
                    nowAmount = card.totalMoney.toFloat(),
                    goalAmount = card.goalMoney.toFloat(),
                    imageUrl = card.fundingThumbnail
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 지역 기부 섹션
        Text(text = "지역 기부", style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically  // 라디오 버튼과 텍스트 정렬
            ) {
                RadioButton(
                    selected = donationState == 1,
                    onClick = { donationState = 1 }
                )
                Text(text = "진행 중")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = donationState == 2,
                    onClick = { donationState = 2 }
                )
                Text(text = "완료")
            }

            // 지역 기부 드롭다운 메뉴
            Box {
                TextButton(onClick = { donationExpanded = true }) {
                    Text(text = donationSelectedSort)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = donationExpanded,
                    onDismissRequest = { donationExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("종료 임박순") },
                        onClick = {
                            donationSelectedSort = "종료 임박순"
                            donationExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("최근 등록순") },
                        onClick = {
                            donationSelectedSort = "최근 등록순"
                            donationExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 지역 기부 리스트
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(300.dp)) {
            items(donationCards) { card ->
                FundingCardComposable(
                    title = card.fundingTitle,
                    location = "${card.sido} ${card.sigungu}",
                    startDate = card.startDate,
                    endDate = card.endDate,
                    nowAmount = card.totalMoney.toFloat(),
                    goalAmount = card.goalMoney.toFloat(),
                    imageUrl = card.fundingThumbnail
                )
            }
        }
    }
}

@Composable
fun FundingCardComposable(
    title: String,
    location: String,
    startDate: String,
    endDate: String,
    nowAmount: Float,
    goalAmount: Float,
    imageUrl: String
) {
    val progress = if (goalAmount > 0) nowAmount / goalAmount else 0f

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = location, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "$startDate ~ $endDate", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "${goalAmount.toInt()}원", style = MaterialTheme.typography.bodyMedium)
    }
}

// API 데이터 불러오는 함수
fun fetchFundingData(type: String, state: Int, onSuccess: (List<FundingData>) -> Unit) {
    val call = SearchFundingApi.api.searchGovernmentFundings(type, state)
    call.enqueue(object : Callback<FundingResponse> {
        override fun onResponse(call: Call<FundingResponse>, response: Response<FundingResponse>) {
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { onSuccess(it) }
            }
        }

        override fun onFailure(call: Call<FundingResponse>, t: Throwable) {
            // 실패 처리
        }
    })
}
