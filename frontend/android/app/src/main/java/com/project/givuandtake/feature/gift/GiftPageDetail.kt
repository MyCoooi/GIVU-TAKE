package com.project.givuandtake.feature.gift

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.data.CartItemData
import com.project.givuandtake.core.data.Review
import kotlinx.coroutines.launch
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.givuandtake.core.apis.Gift.GiftReviewApi
import com.project.givuandtake.core.apis.Gift.GiftReviewLikeDeleteApi
import com.project.givuandtake.core.apis.Gift.GiftReviewLikeGetApi
import com.project.givuandtake.core.apis.Gift.GiftReviewLikePostApi
import com.project.givuandtake.core.data.Gift.GiftReviewData
import com.project.givuandtake.core.data.Gift.ReviewData
import com.project.givuandtake.core.data.GiftDetailData
import com.project.givuandtake.core.datastore.TokenManager
import retrofit2.Response

class GiftReviewModel : ViewModel() {

    private val _reviews = mutableStateOf<List<ReviewData>>(emptyList())
    val reviews: State<List<ReviewData>> = _reviews

    private val _isLiked = mutableStateOf(false)
    val isLiked: State<Boolean> = _isLiked

    fun fetchGiftReviews(giftIdx: Int, pageNo: Int = 1, pageSize: Int = 50, isOrderLiked: Boolean = true) {
        viewModelScope.launch {
            try {
                val response: Response<GiftReviewData> = GiftReviewApi.api.getGiftReviewData(
                    giftIdx = giftIdx,
                    pageNo = pageNo,
                    pageSize = pageSize,
                    isOrderLiked = isOrderLiked
                )
                if (response.isSuccessful) {
                    val reviews = response.body()?.data
                    reviews?.let {
                        _reviews.value = it
                    }
                } else {
                    Log.e("GiftReviews", "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("GiftReviews", "Exception: ${e.message}")
            }
        }
    }

    fun checkIfLiked(reviewIdx: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = GiftReviewLikeGetApi.api.GiftReviewLikeGetData(
                    reviewIdx = reviewIdx,
                    authToken = token
                )
                if (response.isSuccessful) {
                    _isLiked.value = response.body()?.data ?: false
                    Log.d("CheckIfLiked", "Review liked status: ${_isLiked.value}")
                } else {
                    Log.e("CheckIfLiked", "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CheckIfLiked", "Exception: ${e.message}")
            }
        }
    }

    fun likeReview(reviewIdx: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = GiftReviewLikePostApi.api.getGiftReviewLikePostData(
                    reviewIdx = reviewIdx,
                    authToken = token
                )
                if (response.isSuccessful) {
                    Log.d("LikeReview", "Review liked successfully")
                    // 성공 시 필요한 추가 처리(예: UI 업데이트)를 여기에 작성
                } else {
                    Log.e("LikeReview", "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("LikeReview", "Exception: ${e.message}")
            }
        }
    }

    fun unlikeReview(reviewIdx: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = GiftReviewLikeDeleteApi.api.GiftReviewLikeDeleteData(
                    reviewIdx = reviewIdx,
                    authToken = token
                )
                if (response.isSuccessful) {
                    Log.d("UnlikeReview", "Review unliked successfully")
                    // You can update the UI here after unliking the review, if needed
                } else {
                    Log.e("UnlikeReview", "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UnlikeReview", "Exception: ${e.message}")
            }
        }
    }
}

object TabState {
    var selectedTabIndex by mutableStateOf(0) // Compose가 상태 변화를 감지할 수 있도록 mutableStateOf 사용
}

@Composable
fun GiftPageDetail(
    giftIdx: Int,  // 상품 ID
    cartItems: MutableState<List<CartItemData>>,  // 장바구니 항목 상태
    navController: NavController,  // 네비게이션 컨트롤러
    GiftViewModel : GiftViewModel = viewModel()  // 뷰 모델
) {
    val giftDetail by GiftViewModel.giftDetail.collectAsState()  // 상품 상세 정보 상태
    Log.d("adsfaeradsf", "$giftIdx")
    val scope = rememberCoroutineScope()  // 코루틴 스코프
    val context = LocalContext.current  // 현재 Context
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"
    val viewModel: GiftReviewModel = viewModel()

    Log.d("giftDetail", "giftDetail : ${giftDetail}")

    LaunchedEffect(giftIdx) {
        viewModel.fetchGiftReviews(giftIdx)
        GiftViewModel.fetchGiftDetail(token = accessToken, giftIdx = giftIdx)
    }
    val reviews by viewModel.reviews

    LaunchedEffect(Unit) {
        scope.launch {
            val result = fetchCartList(accessToken)
            if (result != null) {
                cartItems.value = result
            } else {
                Log.d("cart","Failed to load cart items")
            }
        }
    }

    Scaffold(
        bottomBar = {
            giftDetail?.let { detail ->
                GiftBottomBar(
                    onAddToCart = {
                        scope.launch {
                            // 이미 같은 상품이 장바구니에 있는지 확인
                            val updatedCartItems = cartItems.value.toMutableList()
                            val existingItemIndex = updatedCartItems.indexOfFirst { it.giftName == detail.giftName }
                            if (existingItemIndex != -1) {
                                // 수량 증가 API 호출
                                val existingItem = updatedCartItems[existingItemIndex]
                                val updatedQuantity = existingItem.amount + 1
                                val success = updateCartItemQuantity(context, existingItem.giftIdx, updatedQuantity)
                                if (success) {
                                    updatedCartItems[existingItemIndex] = existingItem.copy(amount = updatedQuantity)
                                }
                            } else {
                                val success = addToCartApi(context, detail.giftIdx, 1)
                                if (success) {
                                    updatedCartItems.add(
                                        CartItemData(
                                            cartIdx = 0,
                                            giftIdx = detail.giftIdx,
                                            giftName = detail.giftName,
                                            giftThumbnail = detail.giftThumbnail ?: "",
                                            userIdx = 0,
                                            amount = 1,
                                            price = detail.price,
                                            sido = detail.corporationSido,
                                            sigungu = detail.corporationSigungu
                                        )
                                    )
                                }
                            }
                            cartItems.value = updatedCartItems  // UI 갱신
                        }
                    },
                    navController = navController,  // 네비게이션 컨트롤러 전달
                    giftDetail = detail  // 상품 정보 전달
                )
            }
        }

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            giftDetail?.let { detail ->
                item {
                    Box() {
                        Image(
                            painter = rememberImagePainter(detail.giftThumbnail),  // 실제 이미지 경로 사용
                            contentDescription = "상품 이미지",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.5f)
                                        ),
                                        startY = 250f,
                                        endY = 0f
                                    )
                                )
                        )
                        GiftTopBar(
                            cartItemCount = cartItems.value.size,
                            onCartClick = {
                                navController.navigate("cart_page")
                            },
                            navController = navController
                        )
                    }
                }

                item {
                    GiftInformation(giftDetail = detail)
                }

                item {
                    GiftTabs(
                        selectedTabIndex = TabState.selectedTabIndex,
                        onTabSelected = { TabState.selectedTabIndex = it }  // 탭 선택 콜백
                    )
                }

                item {
                    when (TabState.selectedTabIndex) {
                        0 -> ProductIntroduction(giftDetail = detail)  // 상품 소개 탭
                        1 -> ProductReview(reviews = reviews, viewModel = viewModel, token = accessToken)  // 리뷰 탭 (더미 데이터)
                        2 -> RelatedRecommendations(navController = navController, location = detail.location)  // 연관 추천 탭
                    }
                }
            } ?: run {
                // 상품 정보가 없을 때 로딩 메시지 표시
                item {
                    Text("Loading...", Modifier.padding(16.dp))
                }
            }
        }
    }
}


@Composable
fun GiftBottomBar(
    onAddToCart: () -> Unit,
    navController: NavController,
    giftDetail: GiftDetailData
) {
    val coroutineScope = rememberCoroutineScope()
    var isAddingToCart by remember { mutableStateOf(false) } // 장바구니 추가 중인지 확인하는 상태

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current  // 현재 Context 가져오기
            Button(
                onClick = {
                    if (!isAddingToCart) { // 장바구니 추가 중이 아니면 실행
                        coroutineScope.launch {
                            isAddingToCart = true // 장바구니 추가 시작

                            // 장바구니 추가 로직 호출
                            try {
                                onAddToCart()  // UI 업데이트 및 장바구니 추가 담당
                            } catch (e: Exception) {
                                println("장바구니 추가 실패: ${e.localizedMessage}")
                            } finally {
                                isAddingToCart = false // 요청 완료 후 다시 버튼 활성화
                            }
                        }
                    }
                },
                enabled = !isAddingToCart, // 요청 중일 때 버튼 비활성화
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text("장바구니")
            }

            Button(
                onClick = {
                    navController.navigate(
                        "payment_page_gift?name=${Uri.encode(giftDetail.giftName)}&location=${Uri.encode(giftDetail.location)}&price=${giftDetail.price}&quantity=1&thumbnailUrl=${Uri.encode(giftDetail.giftThumbnail)}&giftIdx=${giftDetail.giftIdx}" // 썸네일 URL 전달
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
            ) {
                Text("기부하기", color = Color.White)
            }
        }
    }
}





@Composable
fun GiftInformation(giftDetail: GiftDetailData) {
    Spacer(modifier = Modifier.height(16.dp))

    // 주소 정보
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Place,
            contentDescription = "Location icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = giftDetail.location, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))

    }

    Spacer(modifier = Modifier.height(8.dp))

    Divider(
        color = Color(0xFFDAEBFD),
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = giftDetail.giftName,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "${giftDetail.priceFormatted} 원",
        fontSize = 25.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}

@Composable
fun GiftTopBar(
    cartItemCount: Int, // 장바구니 아이템 수
    onCartClick: () -> Unit, // 장바구니 아이콘 클릭 처리
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )   {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.size(40.dp)
        ) {
            IconButton(onClick = { onCartClick() }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24), // 장바구니 아이콘
                    contentDescription = "Cart Icon",
                    tint = Color.White
                )
            }

            if (cartItemCount > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Red, shape = CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = cartItemCount.toString(),
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun GiftTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("상품 소개", "상품 후기", "연관 추천")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        contentColor = Color(0xFFDAEBFD),
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
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                text = { Text(tabTitle) }
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ProductIntroduction(giftDetail: GiftDetailData) {
    Column(modifier = Modifier.padding(16.dp)) {
        val thumbnailUrl = giftDetail.giftContentImage
        val description = giftDetail.giftContent

        if (thumbnailUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 8000.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = thumbnailUrl),
                        contentDescription = "상품 썸네일",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

//            Spacer(modifier = Modifier.height(16.dp))
        }

        // 상품 설명
        if (description != null) {
            Text(
                text = description,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = "",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun ProductReview(reviews: List<ReviewData>, viewModel: GiftReviewModel, token: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        reviews.forEach { review ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberImagePainter(data = review.userProfileImage),
                        contentDescription = "User profile",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFFDAEBFD), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(review.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(review.modifiedDate.substringBefore("T"), fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                Log.d("LikeReview", "$token, ${review.reviewIdx}")
                                viewModel.likeReview(review.reviewIdx, token)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.good),
                            contentDescription = "Example Icon",
                            modifier = Modifier.padding(horizontal = 10.dp).size(16.dp)
                        )
                        Text(
                            text = "${review.likedCount}",
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 리뷰 이미지
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // 가로 크기를 채움
                        .height(200.dp) // 높이를 명시적으로 설정
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDAEBFD),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = review.reviewImage),
                        contentDescription = "Review image",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(review.reviewContent, fontSize = 20.sp, modifier = Modifier.padding(horizontal =  10.dp))
                Spacer(modifier = Modifier.height(20.dp))

                Divider(
                    color = Color(0xFFDAEBFD),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun RelatedRecommendations(navController: NavController, location: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Location information
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_location_on_24),
                contentDescription = "Location icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("$location", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color.Black
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), // 좌우 여백 추가,
                horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gangwondo_wonju),
                    contentDescription = "Map image",
                    modifier = Modifier
                        .size(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp)) // 이미지와 텍스트 사이 간격
                Text(
                    text = "강원특별자치도 원주시는 치악산국립공원을 중심으로 한 자연경관을 자랑하는 지역으로, 수도권과의 접근성이 좋아 관광객들이 자주 찾는 도시입니다.\n" +
                            "\n" +
                            "주요 관광지로는 빼어난 경치를 자랑하는 치악산국립공원이 있으며, 특히 남대봉에서의 전망이 매우 뛰어납니다. 오크밸리는 스키장과 골프장, 그리고 현대적인 예술 작품이 전시된 뮤지엄 산이 있어 사계절 내내 관광객들이 찾는 인기 명소입니다. 또한, 간현유원지는 여름철 피서지로 유명하며, 소금산 출렁다리 개장 이후 많은 관광객을 끌어모으고 있습니다.\n" +
                            "\n" +
                            "역사적으로 중요한 유적지로는 법천사지가 있으며, 국보급 유물인 지광국사탑이 복원될 예정입니다. 이 외에도 고려시대의 세곡미를 보관하던 흥원창, 그리고 박경리 작가의 집필 장소를 기념한 박경리문학공원 등이 있습니다.\n" +
                            "\n" +
                            "원주는 한지의 명맥을 이어온 지역으로, 매년 한지 문화제가 개최되며, 원주의 주요 특산품으로 한지가 유명합니다.",
                    modifier = Modifier.padding(horizontal = 8.dp) // 글자 좌우 여백 추가
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Related places button
        Button(onClick = {
            // 주변 관광지 버튼 클릭 시 지역 정보를 전달하며 관광 페이지로 이동
            val shortLocation = location.takeLast(3).substring(0, 2)

            navController.navigate("attraction?city=$shortLocation")
        }) {
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


