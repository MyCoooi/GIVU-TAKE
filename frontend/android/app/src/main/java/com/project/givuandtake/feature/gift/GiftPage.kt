package com.project.givuandtake.feature.gift.mainpage

import GiftPageDetail
import android.content.Context
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.data.Product
import com.project.givuandtake.core.datastore.dataStore
import com.project.givuandtake.core.datastore.getCartItems
import com.project.givuandtake.feature.gift.addToFavorites
import com.project.givuandtake.feature.gift.getFavoriteProducts
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//@Composable
//fun GiftPage(navController: NavController) {
//    val products = listOf(
//        GiftDetail(1, "상품 1", 10000, "url1", "강원도 평창"),
//        GiftDetail(2, "상품 2", 20000, "url2", "부산"),
//        GiftDetail(3, "상품 3", 30000, "url3", "대구"),
//        GiftDetail(4, "상품 4", 40000, "url4", "광주"),
//        GiftDetail(5, "상품 5", 50000, "url5", "인천"),
//        GiftDetail(6, "상품 6", 60000, "url6", "울산")
//    )
//    val context = LocalContext.current
//    val favoriteProductsFlow = getFavoriteProducts(context)
//    val favoriteProductsSet by favoriteProductsFlow.collectAsState(initial = emptySet())
//
//    val favoriteProducts = products.filter { product ->
//        favoriteProductsSet.contains(product.id.toString())
//    }
//
//    var searchText by remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()  // CoroutineScope 생성
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.logo),
//                            contentDescription = "Logo",
//                            modifier = Modifier.size(48.dp)
//                        )
//                        Text(
//                            text = "우리 고향 기부하기",
//                            color = Color.Black,
//                            modifier = Modifier.weight(1f),
//                            textAlign = TextAlign.Center
//                        )
//                        CartIcon(cartItemCount = 3, onCartClick = {
//                            // Add navigation or action to open the cart
//                            navController.navigate("cart_page") // Assuming you have a cart page
//                        })
//                    }
//                },
//                backgroundColor = Color(0xFFDAEBFD)
//            )
//        },
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 16.dp)
//        ) {
//            item {
//                Text(text = "우리 고향 기부하기", fontWeight = FontWeight.Bold, fontSize = 25.sp)
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//            item {
//                TextField(
//                    value = searchText,
//                    onValueChange = { newText ->
//                        searchText = newText
//                    },
//                    label = { Text("상품 이름 검색") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp)
//                        .background(Color.White)
//                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
//                        .padding(4.dp),
//                    singleLine = true,
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.White,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    )
//                )
//            }
//            item {
//                Text(text = "상품종류 순", fontWeight = FontWeight.Bold, fontSize = 25.sp)
//                FilterButtons_category()
//            }
//            item {
//                Text(text = "맞춤 추천상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//                ProductGrid(
//                    navController = navController,
//                    products = products,
//                    favoriteProducts = favoriteProducts,
//                    onFavoriteToggle = { product ->
//                        // 비동기적으로 addToFavorites 처리
//                        coroutineScope.launch {
//                            addToFavorites(context, product)  // 코루틴 내에서 안전하게 호출
//                        }
//                    }
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }
//}
@Composable
fun GiftPage(navController: NavController) {
    val context = LocalContext.current
    val products = listOf(
        GiftDetail(1, "상품 1", 10000, "url1", "강원도 평창"),
        GiftDetail(2, "상품 2", 20000, "url2", "부산"),
        GiftDetail(3, "상품 3", 30000, "url3", "대구"),
        GiftDetail(4, "상품 4", 40000, "url4", "광주"),
        GiftDetail(5, "상품 5", 50000, "url5", "인천"),
        GiftDetail(6, "상품 6", 60000, "url6", "울산")
    )

    // 장바구니 아이템을 상태로 저장
    val cartItemsFlow = getCartItems(context) // getCartItems는 DataStore나 DB에서 장바구니 정보를 불러오는 함수
    val cartItems by cartItemsFlow.collectAsState(initial = emptyList()) // 초기 상태는 빈 리스트로 설정

    val favoriteProductsFlow = getFavoriteProducts(context)
    val favoriteProductsSet by favoriteProductsFlow.collectAsState(initial = emptySet())

    val favoriteProducts = products.filter { product ->
        favoriteProductsSet.contains(product.id.toString())
    }

    // 스크롤 상태를 추적하기 위한 rememberLazyListState
    val scrollState = rememberLazyListState()
    // TopBar 가시성을 제어할 remember 변수
    var topBarVisible by remember { mutableStateOf(true) }

    // 이전 스크롤 위치를 저장
    var previousScrollOffset by remember { mutableStateOf(0) }

    // 스크롤 이벤트를 감지하여 TopBar의 가시성을 조절
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }
            .collect { currentScrollOffset ->
                // 스크롤이 아래로 내려가면 TopBar를 숨기고, 위로 올라가면 보이도록 설정
                if (currentScrollOffset > previousScrollOffset) {
                    topBarVisible = false // 아래로 스크롤할 때 TopBar 숨기기
                } else if (currentScrollOffset < previousScrollOffset) {
                    topBarVisible = true // 위로 스크롤할 때 TopBar 보이기
                }
                // 현재 스크롤 오프셋을 저장
                previousScrollOffset = currentScrollOffset
            }
    }

    // 전체를 LazyColumn으로 감싸서 스크롤 가능하게 함
    Box(modifier = Modifier.fillMaxSize()) {
        // MiddleContent와 TopBar를 감싼 LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB3C3F4)), // 페이지 기본 배경색 설정
            state = scrollState // 스크롤 상태 전달
        ) {
            item {
                // 상단에 TopBar 높이만큼 여백 추가
                Spacer(modifier = Modifier.height(200.dp)) // TopBar의 높이를 고려하여 Spacer 추가
            }

            // MiddleContent를 스크롤 안에 포함
            item {
                MiddleContent(
                    navController = navController,
                    products = products,
                    favoriteProducts = favoriteProducts,
                    innerPadding = PaddingValues(0.dp) // 이미 LazyColumn 안이므로 패딩은 0으로 설정
                )
            }
        }

        // TopBar를 보이거나 숨기는 애니메이션 처리
        AnimatedVisibility(
            visible = topBarVisible,
            modifier = Modifier
                .align(Alignment.TopCenter) // 화면 상단에 고정
        ) {
            TopBar(
                navController = navController,
                cartItemCount = cartItems.size // 장바구니 아이템 개수 전달
            )
        }
    }
}



@Composable
fun TopBar(navController: NavController, cartItemCount: Int) {
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
                fontWeight = FontWeight.Bold
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
                fontSize = 25.sp
            )
            CartIcon(cartItemCount = cartItemCount, onCartClick = {
                navController.navigate("cart_page") // Cart 페이지로 이동
            })
        }

        // 검색창
        Spacer(modifier = Modifier.height(8.dp)) // 텍스트와 검색창 사이에 간격 추가
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, shape = RoundedCornerShape(24.dp)) // 검색창 둥근 테두리 적용
                .border(1.dp, Color.Black, shape = RoundedCornerShape(24.dp)) // 테두리
                .padding(horizontal = 16.dp), // 내부 패딩
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24), // 검색 아이콘 리소스
                    contentDescription = "Search Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // 검색 아이콘과 텍스트 필드 사이 간격
                TextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    placeholder = { Text("검색어를 입력하세요") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent, // 배경 투명
                        focusedIndicatorColor = Color.Transparent, // 포커스 시 인디케이터 제거
                        unfocusedIndicatorColor = Color.Transparent // 포커스 해제 시 인디케이터 제거
                    ),
                    singleLine = true
                )
            }
        }
    }
}


@Composable
fun MiddleContent(
    navController: NavController,
    products: List<GiftDetail>,
    favoriteProducts: List<GiftDetail>,
    innerPadding: PaddingValues
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFFFFF), // 배경색 설정
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) // 상단을 둥글게 설정
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
                    fontSize = 25.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FilterButtons_category() // 카테고리 버튼

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "맞춤 추천상품",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            ProductGrid(
                navController = navController,
                products = products,
                favoriteProducts = favoriteProducts,
                onFavoriteToggle = { product ->
                    coroutineScope.launch {
                        addToFavorites(context, product)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "맞춤 추천상품",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            ProductGrid(
                navController = navController,
                products = products,
                favoriteProducts = favoriteProducts,
                onFavoriteToggle = { product ->
                    coroutineScope.launch {
                        addToFavorites(context, product)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "맞춤 추천상품",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            ProductGrid(
                navController = navController,
                products = products,
                favoriteProducts = favoriteProducts,
                onFavoriteToggle = { product ->
                    coroutineScope.launch {
                        addToFavorites(context, product)
                    }
                }
            )
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
                painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24), // 장바구니 아이콘
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
    favoriteProducts: List<GiftDetail>,
    onFavoriteToggle: (GiftDetail) -> Unit
) {
    // LazyRow를 감싸는 Box에 테두리 추가
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게 설정
            .border(2.dp, Color.Black.copy(alpha = 0.3f)) // 테두리 추가
            .padding(vertical = 8.dp) // 위아래 여백
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // 내부 여백 추가
            horizontalArrangement = Arrangement.spacedBy(16.dp) // 카드 간격 설정
        ) {
            // 2개씩 묶어서 슬라이드 되도록 설정
            items(products.chunked(2)) { rowProducts ->
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp) // 세로 간격 설정
                ) {
                    rowProducts.forEach { product ->
                        val isFavorite = favoriteProducts.any { it.id == product.id } // 찜 상태 확인
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
    Card(
        shape = RoundedCornerShape(16.dp), // 카드 모서리를 둥글게 설정
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    "gift_page_detail/${product.id}/${product.name}/${product.price}/${product.imageUrl}/${product.location}"
                )
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E7FF)) // 카드 배경색 설정
                .padding(8.dp), // 패딩을 조금 더 좁게 설정
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 상품 이미지와 찜 아이콘을 같은 Box에 배치
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.blank), // 상품 이미지
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f), // 1:1 비율 유지
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { onFavoriteToggle(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd) // 오른쪽 상단에 배치
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // 상품명과 가격
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp, // 텍스트 크기를 조금 줄임
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "$${product.price}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // 위치 정보와 기타 아이콘
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
                    text = product.location,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}




@Composable
fun FilterButtons_category() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween // 아이템들이 한 줄에 균등하게 배치되도록 설정
    ) {
        CategoryButton(text = "지역상품권", icon = painterResource(id = R.drawable.local_product))
        CategoryButton(text = "농축산물", icon = painterResource(id = R.drawable.agriculture_product))
        CategoryButton(text = "수산물", icon = painterResource(id = R.drawable.seafood_product))
        CategoryButton(text = "가공식품", icon = painterResource(id = R.drawable.processed_food))
        CategoryButton(text = "공예품", icon = painterResource(id = R.drawable.craft_product))
    }
}


@Composable
fun CategoryButton(text: String, icon: Painter) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(4.dp) // 최소한의 여백만 유지
    ) {
        // Text positioned above the circle
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 4.dp) // 텍스트와 원형 간 간격 최소화
        )

        // Icon in a circle
        Box(
            modifier = Modifier
                .size(64.dp) // 원형 크기 설정
                .clip(CircleShape)
                .background(Color(0xFFB3C3F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp) // 아이콘 크기
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



