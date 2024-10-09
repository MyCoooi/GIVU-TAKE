package com.project.givuandtake.feature.gift

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.data.CartItemData
import com.project.givuandtake.core.datastore.TokenManager
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun CartPage(navController: NavController, context: Context, cartViewModel: CartViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var cartItems by remember { mutableStateOf(emptyList<CartItemData>()) }
    val scaffoldState = rememberScaffoldState()
//    val cartItems by cartViewModel.cartItems.collectAsState() // ViewModel의 상태를 UI에서 수집
    // 장바구니 데이터를 API에서 불러오는 함수
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val token = TokenManager.getAccessToken(context)
            if (token != null) {
                val result = fetchCartList("Bearer $token")
                if (result != null) {
                    cartItems = result.map { cartItemData ->
                        CartItemData(
                            cartIdx = cartItemData.cartIdx,
                            giftIdx = cartItemData.giftIdx,
                            giftName = cartItemData.giftName,
                            giftThumbnail = cartItemData.giftThumbnail,
                            userIdx = cartItemData.userIdx,
                            price = cartItemData.price,
                            amount = cartItemData.amount,
                            location = cartItemData.location
                        )
                    }
                    cartViewModel.setCartItems(cartItems) // ViewModel에 데이터 설정
                } else {
                    scaffoldState.snackbarHostState.showSnackbar("Failed to load cart items")
                }
            }
        }
    }

    Scaffold(
        topBar = { CartTopBar() },
        bottomBar = { CartBottomBar(navController, cartItems) },  // 결제 버튼에서 데이터 넘기기 추가
        scaffoldState = scaffoldState
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(cartItems) { cartItem ->
                CartItemView(
                    cartItem = cartItem,
                    onQuantityChange = { item, newQuantity ->
                        coroutineScope.launch {
                            val result = updateCartItemQuantity(context, item.cartIdx, newQuantity)
                            if (result) {
                                // 수량 변경 성공 시 UI 업데이트
                                val updatedItems = cartItems.map {
                                    if (it == item) it.copy(amount = newQuantity) else it
                                }
                                cartItems = updatedItems
                                cartViewModel.setCartItems(updatedItems) // ViewModel에 UI 갱신
                            }
                        }
                    },
                    onDeleteItem = { item ->
                        coroutineScope.launch {
                            val result = deleteCartItem(context, item.cartIdx)
                            if (result) {
                                // 삭제 성공 시 UI 업데이트
                                cartItems = cartItems.filter { it != item }
                                cartViewModel.setCartItems(cartItems) // ViewModel에 데이터 갱신
                                val updatedItems = cartItems.filter { it != item }
                                cartViewModel.setCartItems(updatedItems) // ViewModel에 데이터 갱신

                            }
                        }
                    },
                    context = context
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun CartTopBar() {
    TopAppBar(
        title = { Text("장바구니", color = Color.Black) },
        backgroundColor = Color(0xFFDAEBFD)
    )
}

@Composable
fun CartBottomBar(navController: NavController, cartItems: List<CartItemData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 결제 총 금액 계산
        val totalAmount = cartItems.sumOf { it.price * it.amount }

        // 결제 총 금액 표시
        Text(text = "결제 총 금액", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "₩${String.format("%,d", totalAmount)}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4099E9))

        Spacer(modifier = Modifier.height(8.dp))

        // 결제하기 버튼
        Button(
            onClick = {
                if (cartItems.isNotEmpty()) {
                    // 첫 번째 cartItem을 URL 매개변수로 전달
                    val firstItem = cartItems[0]
                    val name = "${firstItem.giftName}..포함 ${cartItems.size}개"
                    navController.navigate(
                        "payment_page_gift?name=${name}&location=${firstItem.location}&price=${totalAmount}&quantity=${firstItem.amount}&thumbnailUrl=${firstItem.giftThumbnail}&giftIdx=${firstItem.giftIdx}"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
        ) {
            Text(text = "결제하기", color = Color.White)
        }
    }
}

@Composable
fun CartItemView(
    cartItem: CartItemData,
    onQuantityChange: (CartItemData, Int) -> Unit,
    onDeleteItem: (CartItemData) -> Unit,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(data = cartItem.giftThumbnail),  // 실제 썸네일 URL 사용
                    contentDescription = "상품 이미지",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = cartItem.giftName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "수량: ${cartItem.amount}", fontSize = 14.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // 아이콘과 텍스트를 세로로 중앙 정렬
                        horizontalArrangement = Arrangement.Start // 아이템 간 간격을 설정 (필요에 따라 조정 가능)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24), // 이미지 리소스를 불러옴
                            contentDescription = "원산지",
                            modifier = Modifier.size(16.dp), // 아이콘 크기 설정
                            tint = Color.Gray // 아이콘 색상 설정
                        )
                        Spacer(modifier = Modifier.width(2.dp)) // 아이콘과 텍스트 사이 간격
                        Text(
                            text = cartItem.location ?: "위치 정보 없음", // location이 null이면 기본 텍스트 표시
                            fontSize = 14.sp, // 텍스트 크기 설정
                            color = Color.Gray // 텍스트 색상 설정
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = {
                    coroutineScope.launch {
                        onDeleteItem(cartItem)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete Item",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        if (cartItem.amount > 1) {
                            coroutineScope.launch {
                                val result = updateCartItemQuantity(context, cartItem.cartIdx, cartItem.amount - 1)
                                if (result) {
                                    onQuantityChange(cartItem, cartItem.amount - 1)
                                }
                            }
                        }
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
                ) {
                    Text("-", color = Color.White, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${cartItem.amount}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val result = updateCartItemQuantity(context, cartItem.cartIdx, cartItem.amount + 1)
                            if (result) {
                                onQuantityChange(cartItem, cartItem.amount + 1)
                            }
                        }
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
                ) {
                    Text("+", color = Color.White, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(text = "₩${String.format("%,d", cartItem.price * cartItem.amount)}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
