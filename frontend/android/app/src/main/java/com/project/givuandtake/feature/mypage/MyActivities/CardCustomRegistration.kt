package com.project.givuandtake.feature.mypage.MyActivities

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CardNumberInputField(cardNumber: String) {
    val focusManager = LocalFocusManager.current

    val cardParts = if (cardNumber.length == 16) {
        listOf(
            cardNumber.substring(0, 4),
            cardNumber.substring(4, 8),
            cardNumber.substring(8, 12),
            cardNumber.substring(12, 16)
        )
    } else {
        listOf("", "", "", "")
    }

    val firstPart = remember { mutableStateOf(cardParts[0]) }
    val secondPart = remember { mutableStateOf(cardParts[1]) }
    val thirdPart = remember { mutableStateOf(cardParts[2]) }
    val fourthPart = remember { mutableStateOf(cardParts[3]) }

    val firstFocusRequester = remember { FocusRequester() }
    val secondFocusRequester = remember { FocusRequester() }
    val thirdFocusRequester = remember { FocusRequester() }
    val fourthFocusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = firstPart.value,
            onValueChange = { newValue ->
                if (newValue.length <= 4) {
                    firstPart.value = newValue
                    if (newValue.length == 4) {
                        secondFocusRequester.requestFocus()  // 4자리 입력되면 다음 필드로 이동
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(60.dp)
                .padding(4.dp)
                .drawBehind {
                    // 밑줄 추가
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFD6E3FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .focusRequester(firstFocusRequester),
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.Black),
            singleLine = true
        )

        Text(text = "-", modifier = Modifier.padding(4.dp))

        BasicTextField(
            value = secondPart.value,
            onValueChange = { newValue ->
                if (newValue.length <= 4) {
                    secondPart.value = newValue
                    if (newValue.length == 4) {
                        thirdFocusRequester.requestFocus()  // 4자리 입력되면 다음 필드로 이동
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(60.dp)
                .padding(4.dp)
                .drawBehind {
                    // 밑줄 추가
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFD6E3FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .focusRequester(secondFocusRequester),
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.Black),
            singleLine = true
        )

        Text(text = "-", modifier = Modifier.padding(4.dp))

        BasicTextField(
            value = thirdPart.value,
            onValueChange = { newValue ->
                if (newValue.length <= 4) {
                    thirdPart.value = newValue
                    if (newValue.length == 4) {
                        fourthFocusRequester.requestFocus()  // 4자리 입력되면 다음 필드로 이동
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(60.dp)
                .padding(4.dp)
                .drawBehind {
                    // 밑줄 추가
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFD6E3FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .focusRequester(thirdFocusRequester),
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.Black),
            singleLine = true
        )

        Text(text = "-", modifier = Modifier.padding(4.dp))

        BasicTextField(
            value = fourthPart.value,
            onValueChange = { newValue ->
                if (newValue.length <= 4) {
                    fourthPart.value = newValue
                    // 마지막 필드이므로 추가 동작 필요 없음
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(60.dp)
                .padding(4.dp)
                .drawBehind {
                    // 밑줄 추가
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFD6E3FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .focusRequester(fourthFocusRequester),
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.Black),
            singleLine = true
        )
    }
}

@Composable
fun CardCustomRegistration(cardNumber: String, validThru: String, navController: NavController) {
    Log.d("asdfqwer", "$cardNumber, $validThru")
    val validThruParts = remember {
        if (validThru.length == 5 && validThru.contains("/")) {
            validThru.split("/")
        } else {
            listOf("", "")
        }
    }
    var expiryMonth by remember { mutableStateOf(validThruParts[0]) }
    var expiryYear by remember { mutableStateOf(validThruParts[1]) }

    var cvcCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column () {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically, // Row 내 수직 정렬
            horizontalArrangement = Arrangement.SpaceBetween // 좌우 정렬
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
                    .weight(0.3f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "카드등록",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                ,fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Column (
            modifier = Modifier.padding(15.dp)
        ){
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "카드 번호", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(bottom=5.dp))
            CardNumberInputField(cardNumber)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "만료일", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(bottom=5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BasicTextField(
                    value = expiryMonth,
                    onValueChange = { newValue ->
                        if (newValue.length <= 2) {
                            expiryMonth = newValue
                        }
                    },
                    modifier = Modifier
                        .width(30.dp)
                        .padding(bottom = 4.dp),
                    textStyle = LocalTextStyle.current.copy( // 기본 텍스트 스타일 적용
                        fontSize = 20.sp, // 글씨 크기
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword // 숫자 패드만 표시
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box (
                            modifier = Modifier
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx() // 선 두께
                                    val y = size.height - strokeWidth / 2 // 하단에 선 그리기
                                    drawLine(
                                        color = Color(0xFFD6E3FF), // 선 색상
                                        start = androidx.compose.ui.geometry.Offset(0f, y),
                                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                },
                        ){
                            if (expiryMonth.isEmpty()) {
                                Text(
                                    text = "MM",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()  // 실제 입력 필드
                        }
                    }
                )


                Spacer(modifier = Modifier.width(3.dp))
                Text("/")
                Spacer(modifier = Modifier.width(3.dp))

                BasicTextField(
                    value = expiryYear,
                    onValueChange = { newValue ->
                        if (newValue.length <= 2) {
                            expiryYear = newValue
                        }
                    },
                    modifier = Modifier
                        .width(30.dp)
                        .padding(bottom = 4.dp),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword // 숫자 패드만 표시
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box (
                            modifier = Modifier
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color(0xFFD6E3FF),
                                        start = androidx.compose.ui.geometry.Offset(0f, y),
                                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                },
                        ){
                            if (expiryYear.isEmpty()) {
                                Text(
                                    text = "YY",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 보안코드 입력
            Text(text = "보안코드(CVC/CVV)", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(bottom=5.dp))
            BasicTextField(
                value = cvcCode,
                onValueChange = { newValue ->
                    if (newValue.length <= 3) {
                        cvcCode = newValue
                    }
                },
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .width(40.dp), // 하단 선과의 간격
                textStyle = LocalTextStyle.current.copy( // 기본 텍스트 스타일 적용
                    fontSize = 20.sp, // 글씨 크기
                    color = Color.Black
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword // 숫자 패드만 표시
                ),
                singleLine = true,  // 한 줄 입력
                decorationBox = { innerTextField ->
                    Column {
                        innerTextField()  // 실제 입력 필드
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFD6E3FF)) // 하단 선 색상
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 비밀번호 입력
            Text(text = "비밀번호", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(bottom=5.dp))
            BasicTextField(
                value = password,
                onValueChange = { newValue ->
                    if (newValue.length <= 4) { // 4자리까지만 입력 허용
                        password = newValue
                    }
                },
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .width(40.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword // 숫자 패드만 표시
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Column {
                        innerTextField()  // 실제 입력 필드
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFD6E3FF)) // 하단 선 색상
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color(0xFFD6E3FF),
                                        start = androidx.compose.ui.geometry.Offset(0f, y),
                                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                },
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // 카드 등록 버튼
            Button(
                onClick = { /* 카드 등록 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp))
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB3C3F4))
            ) {
                Text(text = "카드 등록하기", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}