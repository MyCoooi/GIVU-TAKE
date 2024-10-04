package com.project.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.project.givuandtake.core.data.KakaoPaymentInfo
import com.project.givuandtake.feature.payment.PaymentMethods_gift
import com.project.givuandtake.feature.payment.PaymentTotalAndButton_gift
import com.project.givuandtake.feature.payment.PaymentViewModel

@Composable
fun PaymentScreen_gift(
    navController: NavController,
    name: String,
    location: String,
    price: Int,
    quantity: Int,
    thumbnailUrl: String,
    giftIdx: Int, // giftIdx 추가
    viewModel: PaymentViewModel = viewModel() // ViewModel 추가
) {
    var selectedMethod by remember { mutableStateOf("KAKAO") } // 결제 수단 상태
    var amount by remember { mutableStateOf(price * quantity) } // 결제 금액을 상품 수량과 가격에 맞게 설정
    val context = LocalContext.current // Composable 내에서 context를 가져옴

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 상품 정보를 표시하는 컴포저블
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
                onMethodSelected = { method -> selectedMethod = method }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 결제 요청 버튼
            Button(
                onClick = {

                    // 결제 정보를 담은 KakaoPaymentInfo 객체 생성
                    val paymentInfo = KakaoPaymentInfo(
                        giftIdx = giftIdx, // giftIdx를 String으로 변환하여 전달
                        paymentMethod = selectedMethod, // 선택된 결제 수단
                        amount = quantity // 총 결제 금액 (가격 * 수량)
                    )

                    // 결제 요청 ViewModel 호출
                    viewModel.preparePayment(
                        navController = navController,
                        context = context, // 현재 Context 전달
                        paymentInfo = paymentInfo // 생성한 결제 정보 전달
                    )
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
            ) {
                Text("결제하기", fontSize = 18.sp)
            }
        }
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
                    // 썸네일 이미지를 표시
                    Image(
                        painter = rememberImagePainter(data = thumbnailUrl), // thumbnailUrl 사용
                        contentDescription = "상품 썸네일",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(100.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = quantity.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
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

