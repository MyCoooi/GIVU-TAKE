package com.project.givuandtake.feature.mypage.MyDonation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.project.givuandtake.core.data.CartItemData
import com.project.givuandtake.core.data.GiftDetail
import com.project.givuandtake.core.datastore.TokenManager
import com.project.givuandtake.feature.gift.CartIcon
import com.project.givuandtake.feature.gift.GiftViewModel
import com.project.givuandtake.feature.gift.addToCartApi
import com.project.givuandtake.feature.gift.fetchCartList

import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Int): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return numberFormat.format(price)
}

@Composable
fun AddToCartBottomSheet(
    product: GiftDetail,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onAddToCart: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x20000000))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(2.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberImagePainter(data = product.giftThumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = product.giftName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "Icon",
                            tint = Color(0xFFA093DE),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(text = product.location, fontSize = 14.sp, color = Color(0xFF8368DC))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(0.dp))
                Row (
                ){
                    Text(text = "${formatPrice(product.price)}원", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(60.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "수량 감소")
                    }
                    Text(text = "$quantity", fontSize = 18.sp) // 기본 수량 1
                    IconButton(onClick = { quantity++ }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "수량 증가")
                    }
                }
                Spacer(modifier = Modifier.width(0.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onAddToCart(quantity) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD9E3FF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "${formatPrice(product.price * quantity)}원 장바구니 담기", fontSize = 16.sp)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Wishlist(
    navController: NavController,
    viewModel: GiftViewModel = viewModel(),
    wishlistViewModel: WishlistViewModel = viewModel()
) {
    val wishlistItems by viewModel.wishlistItems.collectAsState() // GiftViewModel에서 찜 목록 불러옴
    var cartItems by remember { mutableStateOf<List<CartItemData>>(emptyList()) } // API로부터 장바구니 아이템을 관리

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"

    // 장바구니 API에서 데이터를 불러오기
    LaunchedEffect(Unit) {
        val result = fetchCartList(accessToken)
        if (result != null) {
            cartItems = result // 장바구니 데이터 저장
        } else {
            Toast.makeText(context, "장바구니 데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            // 커스텀 TopBar
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                Text(
                    text = "찜 목록",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    CartIcon(cartItemCount = cartItems.size, onCartClick = {
                        navController.navigate("cart_page")
                    })
                }
            }
            Text(
                text = "찜 목록",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                CartIcon(cartItemCount = cartItems.size, onCartClick = {
                    navController.navigate("cart_page")
                })
            }
        }

            Spacer(modifier = Modifier.height(16.dp))

            if (wishlistItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "찜 목록에 아이템이 없습니다.", fontSize = 18.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
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
                            onRemove = { wishlistViewModel.removeItemFromWishlist(it) },
                            onAddToCart = {
                                coroutineScope.launch {
                                    val isAlreadyInCart = cartItems.any { it.giftName == product.giftName }
                                    if (isAlreadyInCart) {
                                        Toast.makeText(context, "이미 장바구니에 있는 상품입니다.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val updatedCartItems = cartItems.toMutableList().apply {
                                            add(
                                                CartItemData(
                                                    cartIdx = 0, // 서버에서 받은 cartIdx를 나중에 업데이트
                                                    giftIdx = product.giftIdx,
                                                    giftName = product.giftName,
                                                    giftThumbnail = product.giftThumbnail ?: "",
                                                    userIdx = 0, // 사용자 ID를 필요 시 할당
                                                    amount = 1,
                                                    price = product.price,
                                                    location = product.location
                                                )
                                            )
                                        }
                                        val success = addToCartApi(context, product.giftIdx, 1) // API를 통해 장바구니에 추가
                                        if (success) {
                                            cartItems = updatedCartItems // 장바구니 데이터 업데이트
                                        } else {
                                            Toast.makeText(context, "장바구니에 추가 실패", Toast.LENGTH_SHORT).show()
                                        }
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
    if (showBottomSheet && selectedProduct != null) {
        AddToCartBottomSheet(
            product = selectedProduct!!,
            showBottomSheet = true,
            onDismiss = { showBottomSheet = false },
            onAddToCart = { quantity ->
                coroutineScope.launch {
                    val updatedCartItems = cartItems.toMutableList().apply {
                        add(
                            CartItem(
                                name = selectedProduct!!.giftName,
                                price = selectedProduct!!.price,
                                quantity = quantity, // 선택한 수량을 반영
                                location = selectedProduct!!.location
                            )
                        )
                    }
                    saveCartItems(context, updatedCartItems) // 장바구니 저장
                    showBottomSheet = false // 바텀 시트 닫기
//                        navController.navigate("cart_page")
                }
            }
        )
    }
}

@Composable
fun WishListItem(
    product: GiftDetail,
    onRemove: (GiftDetail) -> Unit,
    onAddToCartPopUp: (GiftDetail) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 상품 이미지
            Image(
                painter = if (product.giftThumbnail != null) {
                    rememberImagePainter(data = product.giftThumbnail)
                } else {
                    painterResource(id = R.drawable.placeholder)  // drawable 폴더의 기본 이미지 사용
                },
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Transparent),
                contentScale = ContentScale.FillBounds
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
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove to Cart",
                        tint = Color(0xFFA093DE)
                    )
                    Text(text = product.location, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = product.giftName, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = { onAddToCart(product) },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = Color(0xFFA093DE)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("담기")
                }
            }
        }

        IconButton(
            onClick = { onRemove(product) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Remove",
                tint = Color(0xFFFF6F6F)
            )
        }
    }
}


