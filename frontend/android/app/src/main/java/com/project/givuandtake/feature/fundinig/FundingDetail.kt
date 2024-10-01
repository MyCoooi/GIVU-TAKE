package com.project.givuandtake.feature.fundinig

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.project.givuandtake.core.apis.Funding.CommentData
import com.project.givuandtake.core.apis.Funding.CommentResponse
import com.project.givuandtake.core.apis.Funding.FundingCommentsApi
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

    // 응원 메시지 데이터를 위한 상태 변수
    var comments by remember { mutableStateOf<List<CommentData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) } // 데이터 로딩 상태

    // 펀딩 상세 데이터 로드
    LaunchedEffect(fundingIdx) {
        fetchFundingDetail(fundingIdx) { detail ->
            fundingDetail = detail
        }

        // API 호출로 응원 메시지 데이터 가져오기
        fetchFundingComments(fundingIdx) { response ->
            comments = response.data
            isLoading = false
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
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    // 위치 및 제목
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "위치 아이콘",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${detail.sido} ${detail.sigungu}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = detail.fundingTitle,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    // 기간 표시
                    Text(text = "${detail.startDate} ~ ${detail.endDate}")

                    // 목표 금액과 모금된 금액
                    val formattedGoalAmount =
                        NumberFormat.getNumberInstance(Locale.KOREA).format(detail.goalMoney)
                    val formattedTotalMoney =
                        NumberFormat.getNumberInstance(Locale.KOREA).format(detail.totalMoney)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "기부 총액 ",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.alignByBaseline()
                        )
                        Text(
                            text = "${formattedTotalMoney}",
                            fontSize = 20.sp,
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.alignByBaseline()
                        )
                        Text(
                            text = "원",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.alignByBaseline()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = if (detail.goalMoney > 0) detail.totalMoney / detail.goalMoney.toFloat() else 0f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50)),
                        color = Color.Blue
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${(detail.totalMoney / detail.goalMoney.toFloat() * 100).toInt()}%",
                        color = Color.Blue
                    )

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
                                            scrollState.animateScrollTo(scrollState.maxValue)
                                        }
                                    }
                                },
                                text = { Text(tabTitle) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }

                when (selectedTabIndex) {
                    0 -> {
                        Text(text = detail.fundingContent)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    1 -> {
                        var commentText by remember { mutableStateOf("") }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "따뜻한 댓글을 남겨주세요",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OutlinedTextField(
                                value = commentText,
                                onValueChange = { commentText = it },
                                placeholder = { Text("댓글 남기기") },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White,
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    // 댓글 작성 처리 로직 추가
                                },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .size(width = 60.dp, height = 30.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.White
                                ),
                                border = BorderStroke(1.dp, Color.Gray),
                                contentPadding = PaddingValues(
                                    horizontal = 0.dp,
                                    vertical = 0.dp
                                )
                            ) {
                                Text("작성", color = Color.Black)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            Text(
                                text = "댓글 ${comments.size}개",
                                fontWeight = FontWeight.Bold
                            )

                            // 로딩 상태 표시
                            if (isLoading) {
                                Text("댓글을 불러오는 중입니다...")
                            } else {
                                // 실제 응원 메시지 데이터 표시
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    comments.forEach { comment ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp)
                                        ) {
                                            AsyncImage(
                                                model = "https://example.com/profile_image.png",
                                                contentDescription = "프로필 이미지",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(20.dp))
                                                    .border(
                                                        1.dp,
                                                        Color.Gray,
                                                        RoundedCornerShape(20.dp)
                                                    ),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Column {
                                                Text(
                                                    text = comment.name,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )

                                                Box(
                                                    modifier = Modifier
                                                        .border(
                                                            1.dp,
                                                            Color.LightGray,
                                                            RoundedCornerShape(8.dp)
                                                        )
                                                        .padding(8.dp)
                                                ) {
                                                    Text(
                                                        text = comment.commentContent,
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    2 -> Text(text = "기부후기: 아직 추가되지 않았습니다.")
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text("아직 응원메시지가 없습니다.")
            }
        }
    }
}

// 펀딩 상세 데이터 가져오기
fun fetchFundingDetail(fundingIdx: Int, onSuccess: (FundingDetailData) -> Unit) {
    val call = FundingDetailApi.api.getFundingDetail(fundingIdx)
    call.enqueue(object : Callback<FundingDetailResponse> {
        override fun onResponse(call: Call<FundingDetailResponse>, response: Response<FundingDetailResponse>) {
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { onSuccess(it) }
            }
        }

        override fun onFailure(call: Call<FundingDetailResponse>, t: Throwable) {
            // 에러 처리
        }
    })
}

// 응원 메시지 가져오기
fun fetchFundingComments(fundingIdx: Int, onSuccess: (CommentResponse) -> Unit) {
    val call = FundingCommentsApi.api.getFundingComments(fundingIdx)
    call.enqueue(object : Callback<CommentResponse> {
        override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.let { onSuccess(it) }
            }
        }

        override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
            // 에러 처리
        }
    })
}
