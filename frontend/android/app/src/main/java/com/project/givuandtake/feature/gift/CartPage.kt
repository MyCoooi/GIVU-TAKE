package com.project.givuandtake.feature.gift

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.givuandtake.R
import com.project.givuandtake.core.data.CartItem

@Composable
fun CartPage(navController: NavController, cartItems: MutableState<List<CartItem>>) {
    Scaffold(
        topBar = {
            CartTopBar()
        },
        bottomBar = {
            CartBottomBar(totalAmount = cartItems.value.sumOf { it.price * it.quantity })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(cartItems.value) { cartItem ->
                CartItemView(cartItem = cartItem, onQuantityChange = { item, newQuantity ->
                    // 수량 변경 시 처리 로직
                    val updatedItems = cartItems.value.map {
                        if (it == item) it.copy(quantity = newQuantity) else it
                    }
                    cartItems.value = updatedItems // 업데이트된 리스트로 상태 업데이트
                })
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
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // 결제 총 금액
        Text(text = "결제 총 금액", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "₩${totalAmount}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4099E9))

        Spacer(modifier = Modifier.height(8.dp))

        // 결제하기 버튼
        Button(
            onClick = { /* TODO: Navigate to payment */ },
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
    cartItem: CartItem,
    onQuantityChange: (CartItem, Int) -> Unit // 수량 변경을 처리하는 콜백 함수
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 상품 정보
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder), // 상품 이미지
                    contentDescription = "상품 이미지",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = "${cartItem.name}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "수량: ${cartItem.quantity}", fontSize = 14.sp)
                    Text(text = "${cartItem.location}", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 수량 조절 버튼
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        // 수량 감소 로직 (최소 1개 이상이어야 함)
                        if (cartItem.quantity > 1) {
                            onQuantityChange(cartItem, cartItem.quantity - 1)
                        }
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
                ) {
                    Text("-", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "${cartItem.quantity}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        // 수량 증가 로직
                        onQuantityChange(cartItem, cartItem.quantity + 1)
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
                ) {
                    Text("+", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                // 총 구매 금액
                Text(text = "₩${cartItem.price * cartItem.quantity}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCartPage() {
    val navController = rememberNavController()

    // MutableState를 사용하여 상태를 관리
    val cartItems = remember { mutableStateOf(
        listOf(
            CartItem(name = "상품 1", price = 50000, quantity = 1, location = "원주시"),
            CartItem(name = "상품 2", price = 50000, quantity = 1, location = "원주시"),
            CartItem(name = "상품 3", price = 50000, quantity = 1, location = "원주시")
        )
    ) }

    CartPage(navController = navController, cartItems = cartItems)
}
