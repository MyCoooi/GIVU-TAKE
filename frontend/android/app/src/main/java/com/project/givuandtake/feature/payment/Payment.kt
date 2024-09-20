package com.project.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun PaymentScreen(navController: NavController) {
    val scrollState = rememberScrollState() // 스크롤 상태 기억

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White // Set the entire screen background to white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // 스크롤 가능하게 설정
        ) {
            TopBar(navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            // Payment project information section
            PaymentProjectInfo()

            Spacer(modifier = Modifier.height(24.dp))

            // Payment methods section
            PaymentMethods()

            Spacer(modifier = Modifier.height(32.dp))

            // 결제 총 금액 및 버튼 섹션
            PaymentTotalAndButton()

            // 불필요한 추가 여백을 방지하기 위해 Spacer 제거
            // Spacer(modifier = Modifier.height(64.dp)) <- 이 부분을 제거
        }
    }
}


@Composable
fun TopBar(navController: NavController) {
    // Updated TopBar with background color
    Surface(
        color = Color(0xFFFFFFFF), // Set the background color for the top bar
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 0.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "결제하기",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // Ensure text color contrasts with the background
            )
        }
    }
}


@Composable
fun AmountInputField(
    inputText: String,
    onInputChange: (String) -> Unit,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (isFocused) Color.Black else Color.Gray)
    ) {
        BasicTextField(
            value = inputText,
            onValueChange = { onInputChange(it) },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = if (inputText.isNotEmpty() || isFocused) Color.Black else Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    if (inputText.isEmpty() && !isFocused) {
                        Text("금액을 직접 입력해주세요", color = Color.Gray)
                    }
                    innerTextField() // 실제 텍스트 필드 표시
                }
            },
            cursorBrush = SolidColor(Color.Black), // 커서 색상 설정
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChange(focusState.isFocused)
                }
        )
    }
}

@Composable
fun PaymentProjectInfo() {
    var inputText by remember { mutableStateOf("") } // 금액 입력 상태
    var isFocused by remember { mutableStateOf(false) } // 포커스 여부를 상태로 저장
    var currentAmount by remember { mutableStateOf(0) } // 현재 금액 상태

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 좌우 16dp, 상하 8dp의 패딩 추가
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 내부 컴포넌트 간의 패딩
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE0E0E0),
                    modifier = Modifier.size(100.dp) // 이미지 크기 조정
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 텍스트들을 이미지 높이에 맞춰서 균등하게 배치
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(100.dp) // 이미지 높이와 맞춤
                ) {
                    Text(
                        text = "재난 재해",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "태풍 매미 재난 지원 사업",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    // 경상남도 합천군 텍스트와 아이콘을 함께 Row에 배치
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray, // 아이콘 색상
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // 아이콘과 텍스트 사이 간격
                        Text(
                            text = "경상남도 합천군",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 분리된 금액 입력 필드 사용
            AmountInputField(
                inputText = if (currentAmount == 0) "" else currentAmount.toString(),
                onInputChange = { newValue -> currentAmount = newValue.toIntOrNull() ?: 0 },
                isFocused = isFocused,
                onFocusChange = { isFocused = it }
            )

            AmountButtonsRow { amountToAdd ->
                currentAmount += amountToAdd
            } // 금액 버튼을 눌렀을 때 호출되는 콜백
        }
    }
}

@Composable
fun AmountButtonsRow(onAmountAdd: (Int) -> Unit) {
    // 금액 입력 버튼들을 별도의 Composable로 분리
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AmountButton(text = "+5천", modifier = Modifier.weight(1f)) {
            onAmountAdd(5000)
        }
        AmountButton(text = "+1만", modifier = Modifier.weight(1f)) {
            onAmountAdd(10000)
        }
        AmountButton(text = "+5만", modifier = Modifier.weight(1f)) {
            onAmountAdd(50000)
        }
        AmountButton(text = "+10만", modifier = Modifier.weight(1f)) {
            onAmountAdd(100000)
        }
    }
}

@Composable
fun AmountButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(text = text)
    }
}

@Composable
fun PaymentMethods() {
    var selectedMethod by remember { mutableStateOf("") } // 선택된 결제 수단 상태

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 좌우 16dp, 상하 8dp의 패딩 추가
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Column 내부에도 패딩 추가 가능
        ) {
            Text(
                text = "결제 수단",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 결제 수단을 두 개씩 나란히 배치
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PaymentMethodButtonWithIcon(
                        "신용 / 체크 카드",
                        R.drawable.logo,
                        Modifier.weight(1f),
                        selected = selectedMethod == "신용 / 체크 카드", // 선택 상태 전달
                        onClick = { selectedMethod = "신용 / 체크 카드" } // 선택된 결제 수단 업데이트
                    )
                    PaymentMethodButtonWithIcon(
                        "카카오 페이",
                        R.drawable.kakao,
                        Modifier.weight(1f),
                        selected = selectedMethod == "카카오 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "카카오 페이" } // 선택된 결제 수단 업데이트
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PaymentMethodButtonWithIcon(
                        "네이버 페이",
                        R.drawable.naver,
                        Modifier.weight(1f),
                        selected = selectedMethod == "네이버 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "네이버 페이" } // 선택된 결제 수단 업데이트
                    )
                    PaymentMethodButtonWithIcon(
                        "토스 페이",
                        R.drawable.toss_logo,
                        Modifier.weight(1f),
                        selected = selectedMethod == "토스 페이", // 선택 상태 전달
                        onClick = { selectedMethod = "토스 페이" } // 선택된 결제 수단 업데이트
                    )
                }

                // 신용/체크 카드나 카카오 페이가 선택된 경우에만 카드사 선택 표시
                if (selectedMethod == "신용 / 체크 카드" || selectedMethod == "카카오 페이") {
                    CardSelectionDropdown()
                }
            }
        }
    }
}

@Composable
fun PaymentMethodButtonWithIcon(
    methodName: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    selected: Boolean, // 선택 상태를 인자로 추가
    onClick: () -> Unit // 클릭 이벤트를 추가
) {
    val backgroundColor = if (selected) Color(0xFFDAEBFD) else Color(0xFFFFFFFF) // 선택 여부에 따라 배경색 변경

    Box(
        modifier = modifier
            .clickable(onClick = onClick) // 클릭 이벤트 추가
            .background(backgroundColor, shape = RoundedCornerShape(8.dp)) // 선택된 경우 회색 배경
            .border(BorderStroke(1.dp, Color(0xFFB3C3F4)), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = methodName,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified // 아이콘에 색상을 적용하지 않음, 원래 색상 유지
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = methodName,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}



@Composable
fun CardSelectionDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf("카드사 선택") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }, // Text와 Icon을 같이 클릭할 수 있게 설정
            verticalAlignment = Alignment.CenterVertically, // 아이콘과 텍스트를 수직으로 중앙 정렬
            horizontalArrangement = Arrangement.SpaceBetween // 양 끝으로 배치
        ) {
            Text(
                text = selectedCard,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Dropdown Arrow",
                tint = Color.Gray // 화살표 색상 설정
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                selectedCard = "국민카드"
                expanded = false
            }) {
                Text("국민카드")
            }
            DropdownMenuItem(onClick = {
                selectedCard = "우리카드"
                expanded = false
            }) {
                Text("우리카드")
            }
            DropdownMenuItem(onClick = {
                selectedCard = "신한카드"
                expanded = false
            }) {
                Text("신한카드")
            }
        }
    }
}


@Composable
fun PaymentTotalAndButton() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0XFFFAFAFA), // 배경색을 회색으로 설정
        shape = RoundedCornerShape(8.dp) // 모서리 둥글게 설정 가능
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 내부 패딩 추가
            horizontalArrangement = Arrangement.Center, // 중앙에 배치
            verticalAlignment = Alignment.CenterVertically // 수직 가운데 정렬
        ) {
            // 결제 총 금액을 중앙에 배치
            PaymentTotal()

            Spacer(modifier = Modifier.width(24.dp)) // 총 금액과 버튼 사이 간격 조정

            // 결제하기 버튼을 배치
            PaymentButton()
        }
    }
}


@Composable
fun PaymentTotal() {
    // 결제 총 금액 부분을 중앙으로 배치
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 중앙 정렬
        modifier = Modifier
            .padding(end = 16.dp) // 결제 버튼과의 여백 추가
    ) {
        Text(
            text = "결제 총 금액",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold, // 정규 두께
            color = Color(0xFFB3C3F4) // 연한 파란색
        )
        Spacer(modifier = Modifier.height(4.dp)) // 텍스트 사이 간격
        Text(
            text = "50,000 원",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, // 굵은 텍스트
            color = Color(0xFF000000) // 검정색
        )
    }
}

@Composable
fun PaymentButton() {
    // 결제하기 버튼
    Button(
        onClick = { /* 결제 처리 동작 */ },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(50.dp) // 버튼 높이 설정
            .width(150.dp), // 버튼 너비를 적절하게 설정
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A6CE3))
    ) {
        Text(
            text = "결제하기",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}