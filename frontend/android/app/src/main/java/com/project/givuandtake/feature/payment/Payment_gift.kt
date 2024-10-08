//package com.project.payment
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.project.givuandtake.core.data.KakaoPaymentInfo
//import com.project.givuandtake.feature.payment.PaymentMethods_gift
//import com.project.givuandtake.feature.payment.PaymentTotalAndButton_gift
//import com.project.givuandtake.feature.payment.PaymentViewModel
//
//@Composable
//fun PaymentScreen_gift(
//    navController: NavController,
//    name: String,
//    location: String,
//    price: Int,
//    quantity: Int,
//    thumbnailUrl: String,
//    giftIdx: Int,
//    viewModel: PaymentViewModel = viewModel()
//) {
//    var selectedMethod by remember { mutableStateOf("KAKAO") } // 결제 수단 상태
//    var amount by remember { mutableStateOf(quantity) } // 결제 금액 설정
//    val context = LocalContext.current
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.White
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // 상품 정보를 표시하는 컴포저블
//            PaymentProjectInfo_gift(
//                name = name,
//                location = location,
//                quantity = quantity,
//                thumbnailUrl = thumbnailUrl
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 결제 수단 선택 UI
//            PaymentMethods_gift(
//                selectedMethod = selectedMethod,
//                onMethodSelected = { method -> selectedMethod = method }
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // 결제 총 금액 및 버튼 UI
//            PaymentTotalAndButton2_gift(
//                kakaoPaymentInfo = KakaoPaymentInfo(
//                    giftIdx = giftIdx,
//                    paymentMethod = selectedMethod,
//                    amount = amount
//                ),
//                navController = navController,
//                viewModel = viewModel,
//                price = price
//            )
//        }
//    }
//}
//
//@Composable
//fun PaymentTotalAndButton2_gift(
//    kakaoPaymentInfo: KakaoPaymentInfo,
//    navController: NavController,
//    viewModel: PaymentViewModel,
//    price: Int
//) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        color = Color.White,
//        shadowElevation = 4.dp
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // 결제 총 금액 표시
//            PaymentTotal_gift(kakaoPaymentInfo.amount * price)
//
//            // 결제하기 버튼
//            PaymentButton_gift(
//                kakaoPaymentInfo = kakaoPaymentInfo,
//                navController = navController,
//                viewModel = viewModel
//            )
//        }
//    }
//}
//
//@Composable
//fun PaymentTotal_gift(amount: Int) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(end = 16.dp)
//    ) {
//        Text(
//            text = "결제 총 금액",
//            fontSize = 14.sp,
//            color = Color(0xFF1E88E5) // 파란색
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = "₩${String.format("%,d", amount)}",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//    }
//}
//
//@Composable
//fun PaymentButton_gift(kakaoPaymentInfo: KakaoPaymentInfo, navController: NavController, viewModel: PaymentViewModel) {
//    val context = LocalContext.current
//
//    Button(
//        onClick = {
//            viewModel.preparePayment(navController = navController, context = context, paymentInfo = kakaoPaymentInfo)
//        },
//        shape = RoundedCornerShape(24.dp),
//        modifier = Modifier
//            .height(50.dp)
//            .width(150.dp),
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C88FF)) // 배경색 설정
//    ) {
//        Text(
//            text = "결제하기",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//    }
//}
//
//@Composable
//fun PaymentProjectInfo_gift(name: String, location: String, quantity: Int, thumbnailUrl: String) {
//    Surface(
//        shape = RoundedCornerShape(12.dp),
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        shadowElevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Surface(
//                    shape = RoundedCornerShape(8.dp),
//                    color = Color(0xFFE0E0E0),
//                    modifier = Modifier.size(100.dp)
//                ) {
//                    Image(
//                        painter = rememberImagePainter(data = thumbnailUrl),
//                        contentDescription = "상품 썸네일",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .clip(RoundedCornerShape(8.dp)),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Column(
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.height(100.dp)
//                ) {
//                    Text(
//                        text = name,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Text(
//                        text = "구매 수량 : ${quantity}",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.LocationOn,
//                            contentDescription = "Location",
//                            tint = Color.Gray,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            text = location,
//                            fontSize = 14.sp,
//                            color = Color.Gray
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
//
//
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
import androidx.compose.material.ButtonDefaults
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
import com.project.givuandtake.feature.payment.PaymentViewModel

@Composable
fun PaymentScreen_gift(
    navController: NavController,
    name: String,
    location: String,
    price: Int,
    quantity: Int,
    thumbnailUrl: String,
    giftIdx: Int,
    viewModel: PaymentViewModel = viewModel()
) {
    var selectedMethod by remember { mutableStateOf("KAKAO") } // 결제 수단 상태
    var amount by remember { mutableStateOf(quantity) } // 결제 금액 설정
    val context = LocalContext.current

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

            // 결제 총 금액 및 버튼 UI
            PaymentTotalAndButton2_gift(
                kakaoPaymentInfo = KakaoPaymentInfo(
                    giftIdx = giftIdx,
                    paymentMethod = selectedMethod,
                    amount = amount
                ),
                navController = navController,
                viewModel = viewModel,
                price = price
            )
        }
    }
}

@Composable
fun PaymentTotalAndButton2_gift(
    kakaoPaymentInfo: KakaoPaymentInfo,
    navController: NavController,
    viewModel: PaymentViewModel,
    price: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 결제 총 금액 표시
            PaymentTotal_gift(kakaoPaymentInfo.amount * price)

            // 결제하기 버튼
            PaymentButton_gift(
                kakaoPaymentInfo = kakaoPaymentInfo,
                navController = navController,
                viewModel = viewModel
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
            color = Color(0xFF1E88E5) // 파란색
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

@Composable
fun PaymentButton_gift(kakaoPaymentInfo: KakaoPaymentInfo, navController: NavController, viewModel: PaymentViewModel) {
    val context = LocalContext.current

    Button(
        onClick = {
            viewModel.preparePayment(navController = navController, context = context, paymentInfo = kakaoPaymentInfo)
        },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .height(50.dp)
            .width(150.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C88FF)) // 배경색 설정
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
                        text = "구매 수량 : ${quantity}",
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

