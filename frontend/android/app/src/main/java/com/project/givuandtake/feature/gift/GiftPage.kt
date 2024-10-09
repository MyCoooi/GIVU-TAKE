package com.project.givuandtake.feature.gift

import android.app.Application
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.project.givuandtake.core.data.CartItemData

import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.datastore.TokenDataStore
import com.project.givuandtake.core.datastore.TokenManager
import com.project.givuandtake.feature.gift.GiftViewModel
import com.project.givuandtake.feature.gift.addToFavorites
import com.project.givuandtake.feature.mypage.MyDonation.WishlistViewModel
import com.project.givuandtake.ui.theme.CustomTypography
import com.project.givuandtake.ui.theme.GivuAndTakeTheme
import com.project.givuandtake.ui.theme.gmarketSans
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.format.TextStyle


@Composable
fun GiftPage(
    navController: NavController,
    viewModel: GiftViewModel = viewModel(),
    wishlistViewModel: WishlistViewModel = viewModel()
) {
    val context = LocalContext.current
    val allProducts by viewModel.allGiftDetails.collectAsState()
//    val wishlistItems by viewModel.wishlistItemsIds.collectAsState()
    val wishlistItems by viewModel.wishlistItems.collectAsState()
    // 토큰 불러오기
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"

    // 새로고침 버튼 추가: 새로고침 시 모든 데이터를 삭제하고 다시 불러오기
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.deleteAllGiftDetails() // 모든 데이터를 삭제
            viewModel.fetchGiftsFromApi(accessToken) // 데이터를 다시 불러오기
            isRefreshing = false // 새로고침 완료
        }
    }

    // API에서 데이터를 불러오는 로직 추가
    LaunchedEffect(Unit) {
        viewModel.fetchGiftsFromApi(accessToken) // API 호출
        Log.d("ApiCall", "Authorization 토큰:  $accessToken")
    }

    // 장바구니 항목을 API에서 불러오기
    var cartItems by remember { mutableStateOf<List<CartItemData>>(emptyList()) }
    LaunchedEffect(Unit) {
        val result = fetchCartList(accessToken)
        if (result != null) {
            cartItems = result // API에서 불러온 장바구니 데이터로 갱신
        } else {
            Log.d("CartPage", "장바구니 데이터를 불러오는데 실패했습니다.")
        }
    }

    // 스크롤 상태를 추적하기 위한 rememberLazyListState
    val scrollState = rememberLazyListState()
    var topBarVisible by remember { mutableStateOf(true) }
    var previousScrollOffset by remember { mutableStateOf(0) }

    // 스크롤 이벤트 감지하여 TopBar의 가시성 조절
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }
            .collect { currentScrollOffset ->
                if (currentScrollOffset > previousScrollOffset) {
                    topBarVisible = false
                } else if (currentScrollOffset < previousScrollOffset) {
                    topBarVisible = true
                }
                previousScrollOffset = currentScrollOffset
            }
    }

    // 전체를 LazyColumn으로 감싸서 스크롤 가능하게 함
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB3C3F4)), // 페이지 기본 배경색 설정
            state = scrollState
        ) {
            item {
                Spacer(modifier = Modifier.height(200.dp)) // TopBar 높이만큼 Spacer 추가
            }
            item {
                MiddleContent(
                    navController = navController,
                    products = allProducts,
                    wishlistItems = wishlistItems.map { it.giftIdx.toString() }.toSet(), // wishlistItems를 Set<String>으로 변환
                    onFavoriteToggle = { product ->
                        if (wishlistItems.map { it.giftIdx }.contains(product.giftIdx)) {
                            viewModel.removeFromWishlist(accessToken, wishlistItems.first { it.giftIdx == product.giftIdx }.wishIdx) // 찜 상태에서 제거
                        } else {
                            viewModel.addToWishlist(accessToken, product.giftIdx) // 찜 상태로 추가
                        }
                    },
                    token = accessToken
                )
            }
        }

        // TopBar를 보이거나 숨기는 애니메이션 처리
        AnimatedVisibility(
            visible = topBarVisible,
            modifier = Modifier.align(Alignment.TopCenter) // 화면 상단에 고정
        ) {
            TopBar(
                navController = navController,
                cartItemCount = cartItems.size, // API에서 불러온 장바구니 아이템 개수 전달
                onRefresh = { isRefreshing = true } // 새로고침 콜백 전달
            )
        }
    }
}




@Composable
fun TopBar(
    navController: NavController,
    cartItemCount: Int,
    onRefresh: () -> Unit // 새로고침 콜백을 전달받음
) {
    // 검색어 상태를 TopBar 내부에서 관리
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFB3C3F4)) // 전체 배경색을 설정
            .padding(16.dp) // 전체 패딩 설정
    ) {
        // 로고와 텍스트를 상단에 배치
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth() // Row가 전체 너비를 차지하도록 설정
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // 로고 이미지 리소스
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp) // 이미지 크기 설정
            )
            Spacer(modifier = Modifier.width(8.dp)) // 로고와 텍스트 사이 간격
            Text(
                text = "GIVU & TAKE",
                color = Color(0xFF8E8EBD), // 연한 보라색 텍스트 색상
                fontWeight = FontWeight.Bold,
                style = CustomTypography.titleLarge // CustomTypography 적용
            )
        }

        // 텍스트와 장바구니 아이콘을 같은 Row에 배치
        Spacer(modifier = Modifier.height(8.dp)) // 로고와 텍스트 사이에 간격 추가
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "우리 고향 기부하기",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                style = CustomTypography.bodyLarge // CustomTypography 적용
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 새로고침 아이콘 추가
                IconButton(onClick = { onRefresh() }) {  // 새로고침 콜백 호출
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                }
                Spacer(modifier = Modifier.width(8.dp)) // 아이콘 사이 간격


                // WishList 아이콘 추가
                IconButton(onClick = {
                    navController.navigate("wishlist")
                }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "WishList")
                }
                Spacer(modifier = Modifier.width(8.dp)) // 아이콘 사이 간격
                // Cart 아이콘
                CartIcon(cartItemCount = cartItemCount, onCartClick = {
                    navController.navigate("cart_page") // Cart 페이지로 이동
                })
            }
        }

        // 검색창
        Spacer(modifier = Modifier.height(8.dp)) // 텍스트와 검색창 사이에 간격 추가
        SearchBar(
            searchText = searchText,
            onSearchTextChange = { newText -> searchText = newText }
        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//                .background(Color.White, shape = RoundedCornerShape(24.dp)) // 검색창 둥근 테두리 적용
//                .border(1.dp, Color.Black, shape = RoundedCornerShape(24.dp)) // 테두리
//                .padding(horizontal = 16.dp), // 내부 패딩
//            contentAlignment = Alignment.CenterStart
//        ) {

//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_search_24), // 검색 아이콘 리소스
//                    contentDescription = "Search Icon",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp)) // 검색 아이콘과 텍스트 필드 사이 간격
//
//                TextField(
//                    value = searchText,
//                    onValueChange = { newText -> searchText = newText },
//                    placeholder = { Text("검색어를 입력하세요") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.Transparent, // 배경 투명
//                        focusedIndicatorColor = Color.Transparent, // 포커스 시 인디케이터 제거
//                        unfocusedIndicatorColor = Color.Transparent // 포커스 해제 시 인디케이터 제거
//                    ),
//                    singleLine = true
//                )
//            }
//        }
    }
}


@Composable
fun MiddleContent(
    navController: NavController,
    products: List<GiftDetail>,
    wishlistItems: Set<String>,
    onFavoriteToggle: (GiftDetail) -> Unit,
    token: String
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFFFFF), // 배경색 설정
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp) // 상단을 둥글게 설정
            )
            .padding(horizontal = 16.dp) // 전체 내부 패딩 설정
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 상단 아이콘 및 텍스트
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24), // 검색 아이콘 리소스
                    contentDescription = "Search Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "상품종류 순",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    style = CustomTypography.bodyLarge // CustomTypography 적용
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            CategoryScreen(navController)
            Spacer(modifier = Modifier.height(16.dp))

            // 맞춤 추천상품 텍스트
            Text(
                text = "최신상품",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                style = CustomTypography.bodyLarge, // CustomTypography 적용
                modifier = Modifier.padding(vertical = 16.dp)
            )

            ProductGrid(
                navController = navController,
                products = products,
                wishlistItems = wishlistItems,
                onFavoriteToggle = onFavoriteToggle
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "최근 구매한 지역상품",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                style = CustomTypography.bodyLarge, // CustomTypography 적용
                modifier = Modifier.padding(vertical = 16.dp)
            )
            RecentGiftPage(navController = navController, token = token )
            
        }
    }
}



@Composable
fun CartIcon(cartItemCount: Int, onCartClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.TopEnd, // 배지를 아이콘의 우측 상단에 배치
        modifier = Modifier.size(40.dp)
    ) {
        IconButton(onClick = { onCartClick() }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart, // 장바구니 아이콘
                contentDescription = "Cart Icon",
                tint = Color.Black
            )
        }

        // 장바구니 아이템 개수 표시 (배지)
        if (cartItemCount > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp) // 배지 크기
                    .background(Color.Red, shape = CircleShape) // 배지 모양과 색상
                    .align(Alignment.TopEnd) // 배지를 우측 상단에 배치
            ) {
                Text(
                    text = cartItemCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center) // 텍스트를 배지 중앙에 배치
                )
            }
        }
    }
}



@Composable
fun ProductGrid(
    navController: NavController,
    products: List<GiftDetail>,
    wishlistItems: Set<String>,
    onFavoriteToggle: (GiftDetail) -> Unit
) {
    // LazyRow를 감싸는 Box에 테두리 추가
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게 설정
            .padding(vertical = 8.dp) // 위아래 여백
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // 내부 여백 추가
            horizontalArrangement = Arrangement.spacedBy(16.dp) // 카드 간격 설정
        ) {
            // n개씩 묶어서 슬라이드 되도록 설정
            items(products.chunked(1)) { rowProducts ->
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp) // 세로 간격 설정
                ) {
                    rowProducts.forEach { product ->
                        val isFavorite =  wishlistItems.contains(product.giftIdx.toString()) // 찜 상태 확인
                        ProductCard(
                            product = product,
                            isFavorite = isFavorite,
                            onFavoriteToggle = onFavoriteToggle,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(
    product: GiftDetail,
    navController: NavController,
    isFavorite: Boolean,
    onFavoriteToggle: (GiftDetail) -> Unit,
    modifier: Modifier = Modifier // modifier 추가
) {
    val location = "${product.corporationSido} ${product.corporationSigungu}"
    Log.d("product", "${product}")
    Card(
        shape = RoundedCornerShape(16.dp), // 카드 모서리를 둥글게 설정
        modifier = modifier
            .padding(8.dp)
            .width(200.dp)
            .height(300.dp)
            .clickable {
                navController.navigate("gift_page_detail/${product.giftIdx}")
            },
        elevation = 4.dp,
        backgroundColor = Color(0xFFF7F7FB) // 배경색을 연한 색으로 변경
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 패딩을 설정하여 전체적으로 여백을 확보
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 상품 이미지와 찜 아이콘을 같은 Box에 배치
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // 이미지 높이를 적당히 줄임
                    .clip(RoundedCornerShape(12.dp)) // 이미지를 둥글게 클립
                    .background(Color(0xFFEEEEEE)) // 이미지 배경을 회색으로 설정
                    .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp)) // 테두리 설정
            ) {
                Image(
                    painter = rememberImagePainter(product.giftThumbnail),  // 실제 이미지 경로 사용
                    contentDescription = "상품 이미지",
                    modifier = Modifier
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // 상품명과 찜 아이콘
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.giftName,
                    fontWeight = FontWeight.Bold,
                    style = CustomTypography.bodyLarge, // CustomTypography 적용
                    color = Color.Black // 텍스트 색상 설정
                )
            }

            // 위치 정보
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        modifier = Modifier.size(14.dp), // 아이콘 크기를 줄임
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        style = CustomTypography.bodyLarge, // CustomTypography 적용
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // 가격 정보
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // 가격과 아이콘을 양 끝에 배치
                verticalAlignment = Alignment.CenterVertically // 수직 정렬을 중앙으로 맞춤
            ) {
                // 가격 정보
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp)) // 가격 버튼을 둥글게 설정
                        .background(Color(0xFFD1E9FF)) // 연한 파란색 배경
                        .padding(horizontal = 16.dp, vertical = 8.dp), // 패딩 설정
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₩${product.priceFormatted}",
                        style = CustomTypography.bodyLarge, // CustomTypography 적용
                        fontSize = 14.sp,
                        color = Color.Black // 텍스트 색상 설정
                    )
                }

                // 찜 아이콘
                IconButton(
                    onClick = {
                        Log.d("ProductCard", "Favorite button clicked for product: ${product.giftName}, isFavorite: $isFavorite")
                        onFavoriteToggle(product) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color(0xFFDC143C) else Color(0xFFB3B3B3), // 아이콘 색상을 변경
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CategoryScreen(navController: NavController) {
    FilterButtons_category { categoryIdx ->
        Log.d("category", "Navigating to category/$categoryIdx")
        navController.navigate("category/$categoryIdx") // 네비게이션 호출
    }
}

@Composable
fun FilterButtons_category(onCategorySelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryButton(text = "지역상품권", icon = painterResource(id = R.drawable.local_product)) {
            onCategorySelected(1) // 선택된 카테고리 인덱스를 전달
        }
        CategoryButton(text = "농축산물", icon = painterResource(id = R.drawable.agriculture_product)) {
            onCategorySelected(2)
        }
        CategoryButton(text = "수산물", icon = painterResource(id = R.drawable.seafood_product)) {
            onCategorySelected(3)
        }
        CategoryButton(text = "가공식품", icon = painterResource(id = R.drawable.processed_food)) {
            onCategorySelected(4)
        }
        CategoryButton(text = "공예품", icon = painterResource(id = R.drawable.craft_product)) {
            onCategorySelected(5)
        }
    }
}

@Composable
fun CategoryButton(text: String, icon: Painter, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() } // 버튼 클릭 처리
    ) {
        Text(
            text = text,
            style = CustomTypography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFFB3C3F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}






@Composable
fun FavoriteProductList(navController: NavController, favoriteProducts: List<GiftDetail>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(favoriteProducts) { product ->
            ProductCard(
                product = product,
                navController = navController,
                isFavorite = true, // 찜한 목록이므로 모두 찜 상태
                onFavoriteToggle = { /* TODO: 구현: 찜 목록에서 제거 */ }
            )
        }
    }
}



