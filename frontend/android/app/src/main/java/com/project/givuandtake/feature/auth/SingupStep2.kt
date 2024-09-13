package com.project.givuandtake.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun SignupStep2(navController: NavController) {
    var region by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    // 전체를 감싸는 외부 박스
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFD7C4))  // 전체 배경색을 오렌지색으로 설정
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // 필드들을 세로 중앙으로 배치
        ) {
            // 상단 네비게이션과 타이틀 박스
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFFFD7C4)),
                contentAlignment = Alignment.Center
            ) {
                // 타이틀 텍스트
                Text(
                    text = "GIVU & TAKE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 두 박스를 감싸는 중간 박스
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(500.dp) // 높이를 유지하기 위해 고정
                    .clip(RoundedCornerShape(30.dp))  // 중간 박스의 모서리 둥글게 처리
                    .background(Color.White)  // 중간 박스 배경색
                    .padding(12.dp)  // 중간 박스 패딩
            ) {
                Column(
                    verticalArrangement = Arrangement.Top, // 내부 필드들을 위쪽 정렬
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // o o o 단계를 감싸는 박스
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp) // 중간 박스의 윗부분에 맞춰지도록 패딩 조정
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF9874))  // 활성화된 단계
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))  // 비활성화된 단계
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // 회원가입 텍스트
                    Text(
                        text = "회원가입",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp) // 상하 간격을 위한 패딩
                    )

                    // 지역 선택 입력 필드
                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("지역을 선택해주세요(*필수)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFFF9874),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 전화번호 입력 필드
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("전화번호를 입력해주세요(*필수)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFFF9874),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 생년월일 입력 필드
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = { Text("생년월일을 입력해주세요(*필수)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFFF9874),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 성별 선택 버튼
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { gender = "남성" },
                            colors = if (gender == "남성") ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFFF9874),
                                contentColor = Color.White
                            ) else ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = Color(0xFFFF9874)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFFF9874)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("남성", fontWeight = FontWeight.ExtraBold)
                        }

                        Button(
                            onClick = { gender = "여성" },
                            colors = if (gender == "여성") ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFFF9874),
                                contentColor = Color.White
                            ) else ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = Color(0xFFFF9874)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFFF9874)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("여성", fontWeight = FontWeight.ExtraBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))



                    Spacer(modifier = Modifier.height(24.dp))  // 입력 필드와 버튼 사이에 여백 추가

                    // 다음 버튼을 입력 필드와 함께 박스 안에 배치
                    Button(
                        onClick = { navController.navigate("signup_step3") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9874))
                    ) {
                        Text("다음", fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
