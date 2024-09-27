package com.project.givuandtake.feature.mypage.MyDonation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.data.CartItem
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.datastore.getCartItems
import com.project.givuandtake.core.datastore.saveCartItems
import com.project.givuandtake.feature.gift.GiftViewModel
import com.project.givuandtake.feature.gift.mainpage.CartIcon
import com.project.givuandtake.feature.mypage.MyDonation.WishlistViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WishlistPage(
    navController: NavController,
    viewModel: GiftViewModel = viewModel(),
    wishlistViewModel: WishlistViewModel = viewModel()
) {
    val wishlistItems by viewModel.wishlistItems.collectAsState() // GiftViewModel에서 찜 목록 불러옴
    val cartItemsFlow = getCartItems(LocalContext.current) // 장바구니 아이템 목록 불러옴
    val cartItems by cartItemsFlow.collectAsState(initial = emptyList()) // 초기 상태는 빈 리스트
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // 전체 배경색을 흰색으로 설정
                .padding(16.dp) // 적절한 패딩 추가
        ) {
            // 커스텀 TopBar
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart // 왼쪽 정렬
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                Text(
                    text = "찜 목록",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center) // 텍스트를 가운데 배치
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterEnd), // 장바구니 아이콘을 오른쪽 끝에 배치
                ) {
                    CartIcon(cartItemCount = cartItems.size, onCartClick = {
                        navController.navigate("cart_page") // 장바구니 페이지로 이동
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // TopBar와 내용물 사이에 간격 추가

            // 컨텐츠 부분
            if (wishlistItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "찜 목록에 아이템이 없습니다.", fontSize = 18.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            text = "총 ${wishlistItems.size}개",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    items(wishlistItems) { product ->
                        WishListItem(
                            product = product,
                            onRemove = { wishlistViewModel.removeItemFromWishlist(it) }, // 찜 목록에서 아이템 제거
                            onAddToCart = {
                                coroutineScope.launch {
                                    // 장바구니에 이미 있는지 확인
                                    val isAlreadyInCart = cartItems.any { it.name == product.giftName }
                                    if (isAlreadyInCart) {
                                        // 이미 장바구니에 있는 경우 토스트 메시지 표시
                                        Toast.makeText(context, "이미 장바구니에 있는 상품입니다.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // 장바구니에 상품 추가
                                        val updatedCartItems = cartItems.toMutableList().apply {
                                            add(
                                                CartItem(
                                                    name = product.giftName,
                                                    price = product.price,
                                                    quantity = 1, // 기본 수량을 1로 설정
                                                    location = product.location
                                                )
                                            )
                                        }
                                        saveCartItems(context, updatedCartItems) // 업데이트된 장바구니 항목을 저장
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun WishListItem(
    product: GiftDetail,
    onRemove: (GiftDetail) -> Unit,
    onAddToCart: (GiftDetail) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 상품 이미지
            Image(
                painter = rememberImagePainter(data = product.giftThumbnail),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 상품 정보
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.giftName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = product.location, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${product.price}원", fontSize = 16.sp, color = Color.Gray)

            }

            // 버튼들
            Column {
                OutlinedButton(
                    onClick = { onRemove(product) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    modifier = Modifier
                        .height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove to Cart",
                        tint = Color(0xFFA093DE) // 아이콘 색상 조정
                    )
                    Text("삭제")
                }

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedButton(
                    onClick = { onAddToCart(product) },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = Color(0xFFA093DE) // 아이콘 색상 조정
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("담기")
                }
            }
        }
    }
}

