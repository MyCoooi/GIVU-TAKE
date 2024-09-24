package com.project.givuandtake.auth

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.project.givuandtake.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupStep2(navController: NavController, signupViewModel: SignupViewModel) {
    var address by remember { mutableStateOf("") }
    var addressDetail by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf<Boolean?>(null) }
    var addressName by remember { mutableStateOf("") } // Track address selection type
    var customAddressInput by remember { mutableStateOf("") } // Direct input field for 직접입력

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState, // scaffoldState 설정
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState)
        }
    ) { paddingValues ->
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
                                // 주소 입력 필드 및 주소찾기 버튼
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically // 세로 중앙 정렬
                                ) {
                                    OutlinedTextField(
                                        value = address,
                                        onValueChange = { address = it },
                                        label = { Text("주소") },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 8.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color(0xFFFFA726),
                                            unfocusedBorderColor = Color.Gray
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            // Do nothing when the button is clicked
                                        },
                                        modifier = Modifier
                                            .height(60.dp)
                                            .padding(top = 6.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                0xFFFF9874
                                            )
                                        )
                                    ) {
                                        Text(
                                            "주소 찾기",
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    }
                                }

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

                                // 주소 선택 버튼 (우리집, 회사, 직접입력)
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = { addressName = "우리집" },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = if (addressName == "우리집") Color(0xFFFF9874) else Color.White,
                                            contentColor = if (addressName == "우리집") Color.White else Color(0xFFFF9874)
                                        ),
                                        border = BorderStroke(1.dp, Color(0xFFFF9874)),
                                        modifier = Modifier.weight(1f).padding(8.dp)
                                    ) {
                                        Text("우리집")
                                    }
                                    Button(
                                        onClick = { addressName = "회사" },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = if (addressName == "회사") Color(0xFFFF9874) else Color.White,
                                            contentColor = if (addressName == "회사") Color.White else Color(0xFFFF9874)
                                        ),
                                        border = BorderStroke(1.dp, Color(0xFFFF9874)),
                                        modifier = Modifier.weight(1f).padding(8.dp)
                                    ) {
                                        Text("회사")
                                    }
                                    Button(
                                        onClick = { addressName = "직접입력" },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = if (addressName == "직접입력") Color(0xFFFF9874) else Color.White,
                                            contentColor = if (addressName == "직접입력") Color.White else Color(0xFFFF9874)
                                        ),
                                        border = BorderStroke(1.dp, Color(0xFFFF9874)),
                                        modifier = Modifier.weight(1f).padding(8.dp)
                                    ) {
                                        Text("직접입력")
                                    }
                                }

                                // 직접입력 선택 시, 추가로 입력할 수 있는 필드 표시
                                if (addressName == "직접입력") {
                                    OutlinedTextField(
                                        value = customAddressInput,  // 별도 변수 사용
                                        onValueChange = { customAddressInput = it },
                                        label = { Text("직접입력 주소") },
                                        placeholder = { Text("예) 동생집, 이모집") }, // Placeholder 추가
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color(0xFFFFA726),
                                            unfocusedBorderColor = Color.Gray
                                        )
                                    )
                                }

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

                                // 성별 선택 버튼
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = { isMale = true }, // 남성을 선택하면 true
                                        colors = if (isMale == true) ButtonDefaults.buttonColors(
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
                                        onClick = { isMale = false }, // 여성을 선택하면 false
                                        colors = if (isMale == false) ButtonDefaults.buttonColors(
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
                                        if (isMale == null) {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar("성별을 선택해주세요.")
                                            }
                                        } else {
                                            signupViewModel.address = address
                                            signupViewModel.detailAddress = addressDetail
                                            signupViewModel.isMale = isMale ?: false

                                            navController.navigate("signup_step3")
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(
                                            0xFFFF9874
                                        )
                                    )
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
}
