package com.project.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.project.givuandtake.R
import com.project.givuandtake.core.data.Card.UserCard
import com.project.givuandtake.core.data.KakaoPaymentInfo
import com.project.givuandtake.core.datastore.TokenManager
import com.project.givuandtake.feature.mypage.MyActivities.CardBank
import com.project.givuandtake.feature.mypage.MyActivities.CardViewModel
import com.project.givuandtake.feature.payment.PaymentMethods_gift
import com.project.givuandtake.feature.payment.PaymentViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun PaymentScreen_gift(
    navController: NavController,
    name: String,
    location: String,
    price: Int,
    quantity: Int,
    thumbnailUrl: String,
    giftIdx: Int,
    viewModel: PaymentViewModel = viewModel(),
    cardViewModel: CardViewModel = viewModel() // 카드 정보 불러오는 뷰모델 추가
) {
    var selectedMethod by remember { mutableStateOf("KAKAO") } // 결제 수단 상태
    var selectedCard by remember { mutableStateOf<UserCard?>(null) } // 선택된 카드 상태
    var amount by remember { mutableStateOf(quantity) }
    val context = LocalContext.current

    // 사용자 이름과 주소 (더미 데이터)
    val userName = "홍길동" // 사용자 이름
    val userAddress = "서울특별시 강남구 테헤란로 123" // 사용자 주소

    // 은행 정보 리스트
    val bankList = listOf(
        CardBank("IBK기업은행", R.drawable.ibkbank),
        CardBank("수협은행", R.drawable.seabank),
        CardBank("NH농협", R.drawable.nhbank),
        CardBank("국민은행", R.drawable.kbbank),
        CardBank("신한은행", R.drawable.shinhanbank),
        CardBank("우리은행", R.drawable.webank),
        CardBank("하나은행", R.drawable.onebank),
        CardBank("부산은행", R.drawable.busanbank),
        CardBank("경남은행", R.drawable.gyeongnambank),
        CardBank("대구은행", R.drawable.daegubank),
        CardBank("광주은행", R.drawable.gwangjubank),
        CardBank("전북은행", R.drawable.junbukbank),
        CardBank("제주은행", R.drawable.jejubank),
        CardBank("SC제일은행", R.drawable.scbank),
        CardBank("씨티은행", R.drawable.citybank)
    )

    // 카드 데이터 불러오기
    LaunchedEffect(Unit) {
        cardViewModel.getCardData("Bearer ${TokenManager.getAccessToken(context)}")
    }

    // 등록된 카드 목록
    val registeredCards by cardViewModel.cards

    val totalAmount = price * quantity

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 스크롤 가능한 내용
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // 결제 버튼 영역 확보를 위해 여백 추가
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() } // 뒤로 가기
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "결제 정보",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
                }
                // 사용자 이름과 주소 정보
                UserInfoSection(userName = userName, userAddress = userAddress)
                Spacer(modifier = Modifier.height(16.dp))

                // 상단 상품 정보
                PaymentProjectInfo_gift(
                    name = name,
                    location = location,
                    quantity = quantity,
                    thumbnailUrl = thumbnailUrl
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 결제 수단 선택 UI
                PaymentMethods_gift(
                    selectedMethod = selectedMethod,
                    onMethodSelected = { method -> selectedMethod = method },
                    registeredCards = registeredCards, // 등록된 카드 리스트 전달
                    bankList = bankList, // 은행 정보 전달
                    selectedCard = selectedCard, // 선택된 카드 전달
                    onCardSelected = { card -> selectedCard = card },
                    navController = navController
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 하단에 고정된 결제 총 금액과 결제 버튼
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 결제 금액 표시
            PaymentTotal_gift(totalAmount)

            // 결제 버튼
            PaymentButton_gift(
                kakaoPaymentInfo = KakaoPaymentInfo(
                    giftIdx = giftIdx,
                    paymentMethod = selectedMethod,
                    amount = amount
                ),
                navController = navController,
                viewModel = viewModel,
                selectedMethod = selectedMethod
            )
        }
    }
}

@Composable
fun UserInfoSection(userName: String, userAddress: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shadowElevation = 4.dp // 그림자 추가로 카드처럼 보이게 설정
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 내부 여백 추가
        ) {
            Text(
                text = "이름: $userName",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "주소: $userAddress",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun PaymentTotal_gift(amount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        Text(
            text = "결제 총 금액",
            fontSize = 14.sp,
            color = Color(0xFF1E88E5)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "₩${String.format("%,d", amount)}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

//@Composable
//fun PaymentMethods_gift(
//    selectedMethod: String,
//    onMethodSelected: (String) -> Unit
//) {
//    Column {
//        Text(text = "결제 수단", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            PaymentMethodOption(
//                methodName = "카카오페이",
//                isSelected = selectedMethod == "KAKAO",
//                onClick = { onMethodSelected("KAKAO") }
//            )
//            PaymentMethodOption(
//                methodName = "토스페이",
//                isSelected = selectedMethod == "TOSS",
//                onClick = { onMethodSelected("TOSS") }
//            )
//            PaymentMethodOption(
//                methodName = "페이코",
//                isSelected = selectedMethod == "PAYCO",
//                onClick = { onMethodSelected("PAYCO") }
//            )
//        }
//    }
//}

@Composable
fun PaymentMethodOption(
    methodName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color(0xFF9C88FF) else Color.LightGray
        ),
        modifier = Modifier
            .height(50.dp)
//            .weight(1f)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = methodName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun PaymentButton_gift(
    kakaoPaymentInfo: KakaoPaymentInfo,
    navController: NavController,
    viewModel: PaymentViewModel,
    selectedMethod: String
) {
    val context = LocalContext.current
    val paymentInfoJson = Gson().toJson(kakaoPaymentInfo)
    val encodedPaymentInfoJson = URLEncoder.encode(paymentInfoJson, StandardCharsets.UTF_8.toString())

    Button(
        onClick = {
            if (selectedMethod == "KAKAO") {
                // 카카오페이 결제 로직
                viewModel.preparePayment(navController = navController, context = context, paymentInfo = kakaoPaymentInfo)
            } else if (selectedMethod == "신용,체크 카드") {
                // 신용/체크 카드 결제 로직
                navController.navigate("payment_result/$encodedPaymentInfoJson")
//
            }
        },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C88FF))
    ) {
        Text(
            text = "결제하기",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}


@Composable
fun PaymentProjectInfo_gift(name: String, location: String, quantity: Int, thumbnailUrl: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE0E0E0),
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = thumbnailUrl),
                        contentDescription = "상품 썸네일",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(120.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "구매 수량 : $quantity",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = location,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
