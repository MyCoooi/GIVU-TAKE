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

@Composable
fun GiftPage(navController: NavController) {
    val products = listOf(
        GiftDetail(1, "상품 1", 10000, "url1", "강원도 평창"),
        GiftDetail(2, "상품 2", 20000, "url2", "부산"),
        GiftDetail(3, "상품 3", 30000, "url3", "대구"),
        GiftDetail(4, "상품 4", 40000, "url4", "광주"),
        GiftDetail(5, "상품 5", 50000, "url5", "인천"),
        GiftDetail(6, "상품 6", 60000, "url6", "울산")
    )
    val context = LocalContext.current
    val favoriteProductsFlow = getFavoriteProducts(context)
    val favoriteProductsSet by favoriteProductsFlow.collectAsState(initial = emptySet())

    val favoriteProducts = products.filter { product ->
        favoriteProductsSet.contains(product.id.toString())
    }

    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()  // CoroutineScope 생성

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GIVU & TAKE", color = Color.Black) },
                backgroundColor = Color(0xFFDAEBFD)
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(text = "우리 고향 기부하기", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                TextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    label = { Text("상품 이름 검색") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .background(Color.White)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            item {
                Text(text = "상품종류 순", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                FilterButtons_category()
            }
            item {
                Text(text = "맞춤 추천상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                ProductGrid(
                    navController = navController,
                    products = products,
                    favoriteProducts = favoriteProducts,
                    onFavoriteToggle = { product ->
                        // 비동기적으로 addToFavorites 처리
                        coroutineScope.launch {
                            addToFavorites(context, product)  // 코루틴 내에서 안전하게 호출
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
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
        modifier = Modifier.fillMaxSize().padding(16.dp)
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



