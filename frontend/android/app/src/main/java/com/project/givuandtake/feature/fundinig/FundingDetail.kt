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
import com.example.givuandtake.FundingCard
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundingDetailPage(fundingCard: FundingCard, onBackClick: () -> Unit, navController: NavController) {
    val formattedAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(fundingCard.amounts.first)
    val formattedGoalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(fundingCard.amounts.second)
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("사업소개", "응원메시지", "기부후기")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("펀딩 목록") },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState) // 스크롤 상태 사용
                .fillMaxSize()
        ) {
            // 이미지 표시
            AsyncImage(
                model = fundingCard.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 위치 및 제목
            Text(text = fundingCard.location, style = MaterialTheme.typography.bodyMedium)
            Text(text = fundingCard.title, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // 기간 표시
            Text(text = "${fundingCard.startDate} ~ ${fundingCard.endDate}")

            Spacer(modifier = Modifier.height(8.dp))

            // 목표 금액과 모금된 금액
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "기부 총액", fontSize = 16.sp)
                Text(text = "${formattedAmount}원 / ${formattedGoalAmount}원", fontSize = 16.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ProgressBar 및 비율
            LinearProgressIndicator(
                progress = if (fundingCard.amounts.second > 0) fundingCard.amounts.first / fundingCard.amounts.second else 0f,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${(fundingCard.amounts.first / fundingCard.amounts.second * 100).toInt()}%")

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


            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> {
                    // 사업소개
                    Text(text = "산불 피해 복구 지원 기부 캠페인\n" +
                            "\n" +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                    "\n" +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다." +
                            "이 캠페인의 목적은 산불 피해 지역의 신속한 복구를 지원하고, 피해를 입은 가정들이 다시 안정적인 생활을 할 수 있도록 돕는 것입니다."

                    )
                            Spacer(modifier = Modifier.height(16.dp))
                }
                1 -> {
                    // 응원메시지
                    FundingComment(
                        commentCount = 130,
                        comments = listOf(
                            Comment(1, "김*성", "응원합니다"),
                            Comment(2, "김*성", "불쌍한 아이들이네요. 빨리 구조되길 바랍니다."),
                            Comment(3, "김*성", "응원합니다"),
                            Comment(4, "김*성", "응원합니다")
                        )
                    )
                }
                2 -> Text(text = "기부후기 내용")
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
