package com.project.givuandtake.feature.gift.mainpage

import GiftPageDetail
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.project.givuandtake.core.data.Product

@Composable
fun GiftPage(navController: NavController) {
    val products = listOf(
        Product(1, "상품 1", 10000, "url1", "서울"),
        Product(2, "상품 2", 20000, "url2", "부산"),
        Product(3, "상품 3", 30000, "url3", "대구") ,
        Product(4, "상품 4", 40000, "url4", "광주"),
        Product(5, "상품 5", 50000, "url5", "인천"),
        Product(6, "상품 6", 60000, "url6", "울산")

    )

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GIVU & TAKE", color = Color.Black) },
                backgroundColor = Color(0xFFDAEBFD)
            )
        },
//        bottomBar = {
//            BottomNavBar() // 하단 고정 네비게이션 바
//        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Scaffold의 패딩을 적용
                .padding(horizontal = 16.dp)
        ) {
            item { 
                Text(text = "우리 고향 기부하기", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                // 검색창 추가
                TextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    label = { Text("상품 이름 검색") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .background(Color.White) // 배경을 하얀색으로 설정
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)) // 회색 테두리 추가
                        .padding(4.dp), // 테두리와 텍스트 사이 간격
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White, // 배경 색상을 하얀색으로 설정
                        focusedIndicatorColor = Color.Transparent, // 포커스 상태일 때 기본 테두리 제거
                        unfocusedIndicatorColor = Color.Transparent // 비포커스 상태일 때 기본 테두리 제거
                    )
                )
            }
            item {
                Text(text = "상품종류 순", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                FilterButtons_category()
            }

//            item {
//                Text(text = "상품가격 순", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//                FilterButtons_price()
//                Spacer(modifier = Modifier.height(16.dp))
//            }
            item {
                Text(text = "맞춤 추천상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                ProductGrid(navController, products)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(text = "최고 인기 상품", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                ProductGrid(navController, products)
                Spacer(modifier = Modifier.height(16.dp))
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




//@Composable
//fun FilterButtons_price() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//            0xFFB3C3F4
//        )
//        )) {
//            Text(text = "10,000 이하")
//        }
//        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//            0xFFB3C3F4
//        )
//        )) {
//            Text(text = "30,000 이하")
//        }
//        Button(onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//            0xFFB3C3F4
//        )
//        )) {
//            Text(text = "50,000 이하")
//        }
//    }
//}



@Composable
fun ProductGrid(navController: NavController, products: List<Product>) {
    // LazyRow를 감싸는 Box에 테두리 추가
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게 설정
            .border(0.5.dp, Color.Black.copy(alpha = 0.3f)) // 테두리 추가
            .padding(vertical = 8.dp)
            .background(Color(0xFFDAEBFD))

    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // 내부 여백 추가
            horizontalArrangement = Arrangement.spacedBy(16.dp) // 카드 간격 설정
        ) {
            // 2개씩 묶어서 2개의 Column에 배치
            items(products.chunked(2)) { chunkedProducts ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp) // 각 아이템 간의 간격 설정
                ) {
                    chunkedProducts.forEach { product ->
                        ProductCard(product = product, navController = navController)
                    }
                }
            }
        }
    }
}

//            // 상품 이미지
//            AsyncImage(
//                model = product.imageUrl, // product 객체의 이미지 URL 사용
//                contentDescription = "Product Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp), // 이미지 크기 설정
//                contentScale = ContentScale.Crop // 이미지 비율 맞춤
//            )

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // 상품의 id, name, price, imageUrl, location을 전달하며 상세페이지로 이동
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
            // 상품 이미지
            Image(
                painter = painterResource(id = R.drawable.blank), // 앱 리소스 이미지 사용
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()        // 부모의 너비를 가득 채우되
                    .aspectRatio(1f),      // 가로 세로 비율을 1:1로 설정 (정사각형 비율)
                contentScale = ContentScale.Crop // 이미지 비율 맞춤
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 상품 이름과 가격
            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "$${product.price}", fontSize = 16.sp)

            // 지역 아이콘과 지역명
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn, // 로케이션 아이콘
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray // 아이콘 색상 설정
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = product.location, fontSize = 14.sp) // 지역 정보
            }
        }
    }
}


//@Composable
//fun BottomNavBar() {
//    BottomAppBar(
//        backgroundColor = Color.White,
//        contentColor = Color.Black
//    ) {
//        Text(text = "홈", modifier = Modifier.padding(16.dp))
//        Text(text = "기부", modifier = Modifier.padding(16.dp))
//        Text(text = "마이페이지", modifier = Modifier.padding(16.dp))
//    }
//}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "gift_page") {
        composable("gift_page") { GiftPage(navController) }
        composable(
            "gift_page_detail/{id}/{name}/{price}/{imageUrl}/{address}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("price") { type = NavType.IntType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val price = backStackEntry.arguments?.getInt("price") ?: 0
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            val location = backStackEntry.arguments?.getString("address") ?: ""

            GiftPageDetail(id, name, price, imageUrl, location)
        }
    }
}




@Preview
@Composable
fun preview() {
    val navController = rememberNavController() // Preview를 위한 NavController 생성
    GiftPage(navController) // DonationPage에 NavController 전달
}
