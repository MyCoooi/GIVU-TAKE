package com.project.givuandtake.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 로그인 화면 배경색을 하얀색으로 설정
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // 바탕 하얀색
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 로고 이미지
            Image(
                painter = painterResource(id = R.drawable.logo), // res/drawable에 로고 파일 필요
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 앱 이름 텍스트
            Text(
                text = "GIVU & TAKE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // 블랙으로 변경하여 깔끔한 인상
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 로그인 폼을 담는 박스, 하늘색 배경
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE1F5FE), RoundedCornerShape(12.dp))  // 하늘색 배경과 둥근 모서리
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 이메일 입력 필드
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("이메일") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,  // 흰색 입력칸
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,  // 밑줄 없애기
                        unfocusedIndicatorColor = Color.Transparent,  // 밑줄 없애기
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 비밀번호 입력 필드
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("비밀번호") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,  // 흰색 입력칸
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,  // 밑줄 없애기
                        unfocusedIndicatorColor = Color.Transparent,  // 밑줄 없애기
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 로그인 버튼
                Button(
                    onClick = { /* 로그인 처리 */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF64B5F6))  // 버튼을 하늘색으로 변경
                ) {
                    Text(text = "로그인", color = Color.White)  // 텍스트 색상은 흰색
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 간편 로그인 텍스트
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "간편 로그인", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 간편 로그인 아이콘 (카카오, 네이버, 구글)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.naver),
                        contentDescription = "Kakao",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified  // tint를 적용하지 않도록 설정
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.naver),
                        contentDescription = "Naver",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified  // tint를 적용하지 않도록 설정
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.naver),
                        contentDescription = "Google",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified  // tint를 적용하지 않도록 설정
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 비밀번호 찾기 및 회원가입
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "비밀번호 찾기", color = Color.Gray)
                    Text(text = "회원가입", color = Color.Gray)
                }
            }
        }
    }
}
