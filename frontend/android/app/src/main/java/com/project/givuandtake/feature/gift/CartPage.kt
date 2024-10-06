package com.project.givuandtake.feature.gift

import android.content.Context
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.data.CartItemData
import com.project.givuandtake.core.datastore.TokenManager
import kotlinx.coroutines.launch

@Composable
fun CartPage(navController: NavController, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    var cartItems by remember { mutableStateOf(emptyList<CartItemData>()) }  // 장바구니 항목 상태
    val scaffoldState = rememberScaffoldState()

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
                            giftThumbnail = cartItemData.giftThumbnail,  // 추가된 필드
                            userIdx = cartItemData.userIdx,              // 추가된 필드
                            price = cartItemData.price,
                            amount = cartItemData.amount,
                            location = cartItemData.location
                        )
                    }
                } else {
                    scaffoldState.snackbarHostState.showSnackbar("Failed to load cart items")
                }
            }
        }
    }

    Scaffold(
        topBar = { CartTopBar() },
        bottomBar = { CartBottomBar(totalAmount = cartItems.sumOf { it.price * it.amount }) },
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
                            }
                        }
                    },
                    onDeleteItem = { item ->
                        coroutineScope.launch {
                            val result = deleteCartItem(context, item.cartIdx)
                            if (result) {
                                // 삭제 성공 시 UI 업데이트
                                cartItems = cartItems.filter { it != item }
                            }
                        }
                    },
                    context = context  // context 전달
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
fun CartBottomBar(totalAmount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 결제 총 금액
        Text(text = "결제 총 금액", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "₩$totalAmount", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4099E9))

        Spacer(modifier = Modifier.height(8.dp))

        // 결제하기 버튼
        Button(
            onClick = { /* TODO: 결제 페이지로 이동 */ },
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
                    Text(text = cartItem.location ?: "위치 정보 없음", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = {
                    coroutineScope.launch {
                        val result = deleteCartItem(context, cartItem.cartIdx)
                        if (result) {
                            onDeleteItem(cartItem)
                        }
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

                Text(text = "₩${cartItem.price * cartItem.amount}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
