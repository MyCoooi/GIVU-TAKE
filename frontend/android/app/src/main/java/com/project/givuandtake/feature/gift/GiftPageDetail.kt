import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.data.Review
import kotlinx.coroutines.launch

@Composable
fun GiftPageDetail(id: Int, name: String, price: Int, imageUrl: String, address: String) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { GiftTopBar() },
        bottomBar = { GiftBottomBar() }
    ) { innerPadding ->
        // 전체 스크롤 가능한 컨텐츠를 LazyColumn으로 처리
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                // 상품 정보
                GiftInformation(name, price, address)
            }
            item {
                // 탭
                GiftTabs(selectedTabIndex, onTabSelected = { selectedTabIndex = it })
            }
            item {
                // 탭에 따라 다른 내용을 표시
                when (selectedTabIndex) {
                    0 -> ProductIntroduction()
                    1 -> ProductReview(reviews = dummyReviews)
                    2 -> RelatedRecommendations()
                }
            }
        }
    }
}

@Composable
fun GiftTopBar() {
    TopAppBar(
        title = { Text("상세 페이지", color = Color.Black) },
        backgroundColor = Color(0xFFDAEBFD)
    )
}

@Composable
fun GiftBottomBar() {
    BottomAppBar(backgroundColor = Color.White, elevation = 8.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* 장바구니 추가 */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text("장바구니")
            }

            Button(
                onClick = { /* 기부하기 */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
            ) {
                Text("기부 하기", color = Color.White)
            }
        }
    }
}


@Composable
fun GiftInformation(name: String, price: Int, address: String) {
    Image(
        painter = painterResource(id = R.drawable.placeholder), // 실제 이미지 경로 사용
        contentDescription = "상품 이미지",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(16.dp))

    // 상품 정보
    Text(text = "주소: $address", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "상품 이름: $name", fontWeight = FontWeight.Bold, fontSize = 22.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "가격: $price 원", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun GiftTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("상품 소개", "상품 후기", "연관 추천")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,  // 전체 탭의 배경색을 흰색으로 설정
        contentColor = Color.Blue,     // 선택된 탭의 인디케이터 색상
        modifier = Modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, tabTitle ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    scope.launch {
                        onTabSelected(index)
                    }
                },
                selectedContentColor = Color.Black, // 선택된 탭의 텍스트 색상
                unselectedContentColor = Color.Gray, // 선택되지 않은 탭의 텍스트 색상
                text = { Text(tabTitle) }
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ProductIntroduction() {
    Column(modifier = Modifier.padding(16.dp)) {
        // 상품 소개 내용
        Text(
            text = "As in handbags, the double ring and bar design defines the wallet shape...",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Material & Care\nAll products are made with carefully selected materials...",
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProductReview(reviews: List<Review>) {
    // 전체 내용을 스크롤 가능하게 설정
    Column(
        modifier = Modifier
            .fillMaxSize() // 화면을 가득 채우도록 설정
//            .verticalScroll(rememberScrollState()) // 스크롤 가능하게 설정
            .padding(16.dp)
    ) {
        reviews.forEach { review ->
            // 리뷰 표시
            Column(modifier = Modifier.fillMaxWidth()) {
                // 사용자 프로필과 리뷰 텍스트
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth() // 가로 크기를 채움
                ) {
                    Image(
                        painter = rememberImagePainter(data = review.userProfileUrl),
                        contentDescription = "User profile",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(review.reviewerName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(review.reviewCreateTime, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 리뷰 이미지
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // 가로 크기를 채움
                        .height(300.dp) // 높이를 명시적으로 설정
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = review.imageUrl),
                        contentDescription = "Review image",
                        modifier = Modifier.fillMaxSize() // 가로, 세로 모두 채우기
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 리뷰 텍스트
                Text(review.reviewText, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(24.dp)) // 리뷰들 사이의 간격
            }
        }
    }
}


    @Composable
fun RelatedRecommendations() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Location information
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.blank),
                contentDescription = "Location icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("강원특별자치도 원주시", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.blank),
                contentDescription = "Map image"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Related places button
        Button(onClick = { /* TODO: Show related places */ }) {
            Text(text = "+ 주변 관광지")
        }
    }
}

val dummyReviews = listOf(
    Review(
//        userProfileUrl = "https://s3.example.com/user1-profile.jpg"
        userProfileUrl = R.drawable.blank,  // 유저 프로필 S3 URL
        reviewerName = "김싸피",
        reviewCreateTime = "2024 . 09 . 04",  // 후기 작성 날짜
        reviewText = "치악산 복숭아 당도 최고!",
//        imageUrl = "https://s3.example.com/review1-image.jpg"  // 후기 이미지 S3 URL
        imageUrl = R.drawable.placeholder  // 후기 이미지 S3 URL
    ),
    Review(
        userProfileUrl = R.drawable.blank,  // 유저 프로필 S3 URL
        reviewerName = "이싸피",
        reviewCreateTime = "2024 . 09 . 05",  // 후기 작성 날짜
        reviewText = "복숭아가 정말 달아요!",
        imageUrl = R.drawable.placeholder  // 후기 이미지 S3 URL
    )
)

@Preview
@Composable
fun preview_detail() {
    GiftPageDetail(
        id = 1,
        name = "상품 이름",
        price = 10000,
        imageUrl = "이미지 URL",
        address = "서울시 강남구"
    )
}
