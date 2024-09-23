package com.project.givuandtake.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun SignupStep2(navController: NavController, signupViewModel: SignupViewModel) {
    var postalCode by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressDetail by remember { mutableStateOf("") }
    var isDefaultAddress by remember { mutableStateOf(false) }
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
            verticalArrangement = Arrangement.Center // 필드들을 위쪽으로 정렬
        ) {
            // 상단 네비게이션과 타이틀 박스
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFFFD7C4)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 뒤로가기 버튼
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 뒤로가기 아이콘 추가
                            contentDescription = "뒤로가기",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.7f))

                    // 타이틀 텍스트
                    Text(
                        text = "GIVU & TAKE",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFFFFF)
                    )

                    Spacer(modifier = Modifier.weight(1f)) // 텍스트와 아이콘을 양쪽으로 정렬
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 회원가입과 입력 필드를 감싸는 박스
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))  // 중간 박스의 모서리 둥글게 처리
                    .background(Color.White)  // 중간 박스 배경색
                    .padding(16.dp)  // 중간 박스 패딩
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
                            .padding(top = 16.dp)
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
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 입력 필드들을 감싸는 박스
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))  // 바깥 흰색 박스의 모서리를 둥글게 처리
                            .padding(8.dp)  // 전체 입력 박스 패딩
                    ) {
                        Column {
                            // 우편번호 및 주소찾기 버튼
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically // 세로 중앙 정렬
                            ) {
                                OutlinedTextField(
                                    value = postalCode,
                                    onValueChange = { postalCode = it },
                                    label = { Text("우편번호") },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color(0xFFFFA726),
                                        unfocusedBorderColor = Color.Gray
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = { /* 주소 찾기 처리 */ },
                                    modifier = Modifier
                                        .height(60.dp)
                                        .padding(top = 6.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9874))
                                ) {
                                    Text("주소 찾기", fontWeight = FontWeight.ExtraBold, color = Color.White)
                                }
                            }


                            // 주소 입력 필드
                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("주소") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFFFFA726),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )


                            // 상세 주소 입력 필드
                            OutlinedTextField(
                                value = addressDetail,
                                onValueChange = { addressDetail = it },
                                label = { Text("상세 주소") },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFFFFA726),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // 생년월일 입력 필드
                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = { birthDate = it },
                                label = { Text("생년월일 (ex: 2020-01-09)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                trailingIcon = {
                                    Icon(Icons.Default.DateRange, contentDescription = null)
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFFFFA726),
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

                            Spacer(modifier = Modifier.height(24.dp))

                            // 다음 버튼을 입력 필드와 함께 박스 안에 배치
                            Button(
                                onClick = {
                                    signupViewModel.postalCode = postalCode
                                    signupViewModel.address = address
                                    signupViewModel.addressDetail = addressDetail
                                    signupViewModel.birthDate = birthDate
                                    signupViewModel.gender = gender

                                    navController.navigate("signup_step3")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9874))
                            ) {
                                Text(
                                    "다음", fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
