package com.project.givuandtake.feature.gift.mainpage

import GiftPageDetail
import android.content.Context
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
    val favoriteProductsFlow = getFavoriteProducts(context)
    val favoriteProductsSet by favoriteProductsFlow.collectAsState(initial = emptySet())

    val favoriteProducts = products.filter { product ->
        favoriteProductsSet.contains(product.id.toString())
    }



    Scaffold(
        topBar = {
            TopBar(
                navController = navController
            )
        },
        content = { innerPadding ->
            MiddleContent(
                navController = navController,
                products = products,
                favoriteProducts = favoriteProducts,
                innerPadding = innerPadding
            )
        },
        backgroundColor = Color(0xFFB3C3F4) // 페이지 기본 배경색 설정 (연한 파란색)
    )
}
@Composable
fun TopBar(navController: NavController) {
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
            CartIcon(cartItemCount = 3, onCartClick = {
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
                    modifier = Modifier.fillMaxWidth().height(56.dp),
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
            .padding(innerPadding)
            .background(
                color = Color(0xFFFFFFFF), // 배경색 설정
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) // 상단을 둥글게 설정
            )
            .padding(horizontal = 16.dp) // 전체 내부 패딩 설정
    ) {
        LazyColumn {
            // 상단 아이콘 및 텍스트
            item {
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
            }

            // 맞춤 추천상품
            item {
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
            }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(0.5.dp, Color.Black.copy(alpha = 0.3f))
            .padding(vertical = 8.dp)
            .background(Color(0xFFDAEBFD))
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                val isFavorite = favoriteProducts.any { it.id == product.id }

                ProductCard(
                    product = product,
                    navController = navController,
                    isFavorite = isFavorite,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: GiftDetail,
    navController: NavController,
    isFavorite: Boolean,
    onFavoriteToggle: (GiftDetail) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    "gift_page_detail/${product.id}/${product.name}/${product.price}/${product.imageUrl}/${product.location}"
                )
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.blank),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "$${product.price}", fontSize = 16.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = product.location, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(onClick = { onFavoriteToggle(product) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(24.dp)
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



