package com.project.givuandtake.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                painter = painterResource(id = R.drawable.logo),
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
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 입력칸을 담는 박스 (배경 흰색)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
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
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Gray,  // 테두리 색상 회색
                        unfocusedIndicatorColor = Color.Gray,  // 테두리 색상 회색
                        backgroundColor  = Color.White // 텍스트 필드의 배경색
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
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Gray,  // 테두리 색상 회색
                        unfocusedIndicatorColor = Color.Gray,  // 테두리 색상 회색
                        backgroundColor  = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 로그인 버튼
                Button(
                    onClick = { /* 로그인 처리 */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "이메일로 로그인", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 회원가입 | 비밀번호 찾기 (이메일로 로그인 아래 좌우로 배치)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "비밀번호 찾기",
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                        }
                    )
                    Text(
                        text = "회원가입",
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            navController.navigate("signup_step1")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 간편 로그인 - 선 양옆
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text(
                    text = "간편 로그인",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.Gray
                )
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 간편 로그인 아이콘 (테두리 추가)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.naver),
                    contentDescription = "Naver",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.LightGray, CircleShape),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = R.drawable.kakao),
                    contentDescription = "Kakao",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.LightGray, CircleShape),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .padding(4.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}
