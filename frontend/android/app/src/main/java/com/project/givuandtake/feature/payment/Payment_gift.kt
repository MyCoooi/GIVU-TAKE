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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R
import com.project.givuandtake.core.data.PaymentInfo
import com.project.givuandtake.feature.payment.AmountButtonsRow
import com.project.givuandtake.feature.payment.AmountInputField
import com.project.givuandtake.feature.payment.PaymentMethods
import com.project.givuandtake.feature.payment.PaymentMethods_gift
import com.project.givuandtake.feature.payment.PaymentTotal
import com.project.givuandtake.feature.payment.PaymentTotalAndButton
import com.project.givuandtake.feature.payment.PaymentTotalAndButton_gift
import com.project.givuandtake.feature.payment.PaymentTotal_gift
import com.project.givuandtake.feature.payment.TopBar

@Composable
fun PaymentScreen_gift(navController: NavController, name: String, location: String, price: Int, quantity: Int) {
    var selectedMethod by remember { mutableStateOf("") } // 결제 수단 상태

    // 결제 정보 객체 생성
    val paymentInfo = PaymentInfo(
        selectedGivu = "답례품 구매",
        selectedMethod = selectedMethod,
        amount = price,
        name = name,
        location = location,
        quantity = quantity
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            PaymentProjectInfo_gift(paymentInfo.name, paymentInfo.location, paymentInfo.quantity)
            // 결제 수단을 선택하는 UI
            PaymentMethods_gift(
                selectedMethod = selectedMethod,
                onMethodSelected = { method -> selectedMethod = method }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 결제 총 금액 및 버튼 섹션
            PaymentTotalAndButton_gift(paymentInfo = paymentInfo)
        }
    }
}



@Composable
fun PaymentProjectInfo_gift(name: String, location: String, quantity: Int) {

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
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
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

//            // 금액 입력 필드
//            AmountInputField(
//                inputText = currentAmount.toString(),
//                onInputChange = { newValue -> currentAmount = newValue.toIntOrNull() ?: 0 },
//                isFocused = false,
//                onFocusChange = {}
//            )
//
//            AmountButtonsRow { amountToAdd -> currentAmount += amountToAdd }
        }
    }
}
