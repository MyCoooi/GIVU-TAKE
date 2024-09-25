package com.example.givuandtake

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.project.givuandtake.ui.theme.GivuAndTakeTheme
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

data class FundingCard(
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val amounts: Pair<Float, Float>,
    val imageUrl: String // 이미지 URL 또는 리소스 경로 추가
)

@Composable
fun FundingMainPage(navController: NavController) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // 각각의 섹션에서 독립적인 정렬 및 필터 상태 관리
    var disasterSelectedSort by remember { mutableStateOf("종료 임박순") }
    var disasterSelectedFilter by remember { mutableStateOf("진행 중") }  // 필터 추가
    var donationSelectedSort by remember { mutableStateOf("종료 임박순") }
    var donationSelectedFilter by remember { mutableStateOf("진행 중") }  // 필터 추가

    val disasterCards = listOf(
        FundingCard("태풍 피해 복구 지원 사업", "울산광역시 동구", "2024-07-17", "2024-08-25", Pair(7000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("산불 피해 복구 지원 사업", "경상북도 구미시", "2024-07-11", "2024-09-25", Pair(5000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드1", "위치1", "2024-06-11", "2024-09-25", Pair(3000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드2", "위치2", "2024-05-11", "2024-11-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드3", "위치3", "2024-04-11", "2024-10-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드3", "위치3", "2024-04-11", "2024-10-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg")
    )

    val localDonationCards = listOf(
        FundingCard("유기 동물 구조·보호 지원 사업", "경상남도 하동군", "2024-08-11", "2024-08-25", Pair(3000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("재난 지원 사업", "울산광역시 서구", "2024-09-11", "2024-09-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드3", "위치3", "2024-07-12", "2024-08-25", Pair(3000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드4", "위치4", "2024-07-13", "2024-08-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg"),
        FundingCard("추가 카드5", "위치5", "2024-07-14", "2024-08-25", Pair(2000000f, 10000000f),
            imageUrl = "https://cdn.idomin.com/news/photo/202108/769634_452493_1211.jpg")
    )

    // 필터링된 리스트
    val filteredDisasterCards = if (disasterSelectedFilter == "진행 중") {
        disasterCards.filter { it.endDate > "2024-09-10" }  // 현재 날짜보다 이후인 경우 진행 중
    } else {
        disasterCards.filter { it.endDate <= "2024-09-10" }  // 완료된 경우
    }

    val filteredDonationCards = if (donationSelectedFilter == "진행 중") {
        localDonationCards.filter { it.endDate > "2024-09-10" }
    } else {
        localDonationCards.filter { it.endDate <= "2024-09-10" }
    }

    // 정렬
    val sortedDisasterCards = when (disasterSelectedSort) {
        "최근 등록순" -> filteredDisasterCards.sortedByDescending { parseDate(it.startDate) }
        "종료 임박순" -> filteredDisasterCards.sortedBy { parseDate(it.endDate) }
        else -> filteredDisasterCards
    }

    val sortedDonationCards = when (donationSelectedSort) {
        "최근 등록순" -> filteredDonationCards.sortedByDescending { parseDate(it.startDate) }
        "종료 임박순" -> filteredDonationCards.sortedBy { parseDate(it.endDate) }
        else -> filteredDonationCards
    }

    // Pagination settings
    var currentDisasterPage by remember { mutableStateOf(0) }
    var currentDonationPage by remember { mutableStateOf(0) }
    val itemsPerPage = 4

    val disasterTotalPages = (sortedDisasterCards.size + itemsPerPage - 1) / itemsPerPage
    val donationTotalPages = (sortedDonationCards.size + itemsPerPage - 1) / itemsPerPage

    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(scrollState)) {

        // 재난·재해 섹션
        Text(text = "재난·재해", style = MaterialTheme.typography.titleLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // RadioButtonGroup에 필터 값 전달
            RadioButtonGroup(
                selectedOption = disasterSelectedFilter,
                onOptionSelected = { disasterSelectedFilter = it }
            )
            SortDropdownMenu(selectedSort = disasterSelectedSort, onSortChange = { sortOption ->
                disasterSelectedSort = sortOption
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        val disasterStartIndex = currentDisasterPage * itemsPerPage
        val disasterEndIndex = minOf(disasterStartIndex + itemsPerPage, sortedDisasterCards.size)
        val disasterItemsToShow = sortedDisasterCards.subList(disasterStartIndex, disasterEndIndex)

        val disasterDynamicHeight = when (disasterItemsToShow.size) {
            1, 2 -> 350.dp
            else -> 700.dp
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(disasterDynamicHeight)) {
            items(disasterItemsToShow) { card ->
                FundingCardComposable(
                    card = card,
                    navController = navController  // 네비게이션 추가
                )

            }
        }

        PaginationControls(currentPage = currentDisasterPage, totalPages = disasterTotalPages) { page ->
            coroutineScope.launch {
                scrollState.animateScrollTo(0)  // 페이지 변경 시 상단으로 스크롤
            }
            currentDisasterPage = page
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 지역 기부 섹션
        Text(text = "지역 기부", style = MaterialTheme.typography.titleLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // RadioButtonGroup에 필터 값 전달
            RadioButtonGroup(
                selectedOption = donationSelectedFilter,
                onOptionSelected = { donationSelectedFilter = it }
            )
            SortDropdownMenu(selectedSort = donationSelectedSort, onSortChange = { sortOption ->
                donationSelectedSort = sortOption
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        val donationStartIndex = currentDonationPage * itemsPerPage
        val donationEndIndex = minOf(donationStartIndex + itemsPerPage, sortedDonationCards.size)
        val donationItemsToShow = sortedDonationCards.subList(donationStartIndex, donationEndIndex)

        // 동적 높이 적용
        val donationDynamicHeight = when (donationItemsToShow.size) {
            1, 2 -> 350.dp
            else -> 700.dp
        }


        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(donationDynamicHeight)) {
            items(donationItemsToShow) { card ->
                FundingCardComposable(
                    card = card,
                    navController = navController  // 네비게이션 추가
                )
            }
        }


        PaginationControls(currentPage = currentDonationPage, totalPages = donationTotalPages) { page ->
            coroutineScope.launch {
                scrollState.animateScrollTo(0)  // 페이지 변경 시 상단으로 스크롤
            }
            currentDonationPage = page
        }
    }
}

// 날짜 파싱 함수
fun parseDate(dateString: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    return dateFormat.parse(dateString)?.time ?: 0
}

@Composable
fun FundingCardComposable(card: FundingCard, navController: NavController) {
    val progress = if (card.amounts.second > 0) card.amounts.first / card.amounts.second else 0f  // 비율 계산

    // 숫자 형식을 한국어 로케일로 포맷팅
    val formattedGoalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(card.amounts.second.toInt())

    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // 네비게이션 경로로 이동
                navController.navigate(
                    "funding_detail/${Uri.encode(card.title)}/${Uri.encode(card.location)}/${card.startDate}/${card.endDate}/${card.amounts.first}/${card.amounts.second}/${Uri.encode(card.imageUrl)}"
                )
            }
    ) {
        // 위치 표시
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = card.location, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 이미지 로드 및 표시
        Image(
            painter = rememberAsyncImagePainter(card.imageUrl),  // Coil을 사용하여 이미지 URL로 로드
            contentDescription = null,  // 이미지 설명
            modifier = Modifier
                .aspectRatio(1f)  // 1:1 비율로 너비에 맞춰 높이를 조정
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),  // 둥근 테두리 추가
            contentScale = ContentScale.Crop  // 이미지 크기 조정
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 날짜를 "startDate ~ endDate" 형식으로 표시
        Text(
            text = "${card.startDate} ~ ${card.endDate}",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 제목
        Text(
            text = card.title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ProgressBar와 목표 금액 표시
        androidx.compose.material3.LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 목표 금액을 쉼표로 구분하여 표시
        Text(
            text = "${formattedGoalAmount}원",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun FundingMainPreview() {
    GivuAndTakeTheme {
        val navController = rememberNavController() // Preview를 위한 NavController 생성
        FundingMainPage(navController)
    }
}
