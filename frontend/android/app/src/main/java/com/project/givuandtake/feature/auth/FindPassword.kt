package com.project.givuandtake.feature.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.core.apis.FindPasswordApi
import com.project.givuandtake.core.apis.PasswordCodeRequest
import com.project.givuandtake.core.apis.PasswordCodeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun FindPassword(navController: NavController) {
    // 상태 정의
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var verificationCode by remember { mutableStateOf(TextFieldValue("")) }
    var isCodeSent by remember { mutableStateOf(false) }  // 인증번호 발송 여부
    var errorMessage by remember { mutableStateOf("") }  // 에러 메시지 상태
    var remainingTime by remember { mutableStateOf(0) }  // 남은 시간
    var isButtonEnabled by remember { mutableStateOf(true) } // 버튼 활성화 상태

    val orangeColor = Color(0xFFFF9874) // 오렌지색 정의
    val grayColor = Color(0xFFB0B0B0) // 회색 정의

    // 코루틴 스코프 정의
    val scope = rememberCoroutineScope()

    // 타이머 시작 함수
    fun startTimer() {
        remainingTime = 120  // 2분 = 120초
        isButtonEnabled = false
        scope.launch {
            while (remainingTime > 0) {
                delay(1000L)  // 1초 지연
                remainingTime--
            }
            isButtonEnabled = true // 타이머 끝나면 버튼 활성화
        }
    }

    // 이메일로 인증번호 발송하는 함수 (API 호출)
    fun sendVerificationCode(email: String) {
        val request = PasswordCodeRequest(email)

        FindPasswordApi.api.sendPasswordCode(request).enqueue(object : Callback<PasswordCodeResponse> {
            override fun onResponse(call: Call<PasswordCodeResponse>, response: Response<PasswordCodeResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Log.d("FindPassword", "인증번호 발송 성공: $email")
                    startTimer()
                    isCodeSent = true
                } else {
                    Log.e("FindPassword", "인증번호 발송 실패: ${response.code()} - ${response.message()}")
                    errorMessage = "인증번호 발송에 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PasswordCodeResponse>, t: Throwable) {
                Log.e("FindPassword", "인증번호 발송 오류: ${t.message}")
                errorMessage = "네트워크 오류로 인해 발송에 실패했습니다."
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 상단 뒤로가기 버튼과 제목
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                text = "비밀번호 찾기",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 이메일 입력 필드
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일 주소") },
            modifier = Modifier.fillMaxWidth()
        )

        // 에러 메시지 출력
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 인증번호 발송 버튼
        Button(
            onClick = {
                sendVerificationCode(email.text) // API 호출
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (isButtonEnabled) orangeColor else grayColor),  // 버튼 색상 변경
            enabled = isButtonEnabled  // 버튼 활성화/비활성화
        ) {
            Text(
                text = if (isButtonEnabled) "인증번호 발송" else "남은 시간: ${remainingTime}초",
                color = Color.White
            )
        }

        // 인증번호 발송 후 인증번호 입력 필드 보여주기
        if (isCodeSent) {
            Spacer(modifier = Modifier.height(16.dp))

            // 인증번호 입력 필드
            OutlinedTextField(
                value = verificationCode,
                onValueChange = { verificationCode = it },
                label = { Text("인증번호 입력") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 인증번호 확인 버튼
            Button(
                onClick = {
                    // 인증번호 확인 로직 추가
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangeColor) // 버튼 색상 변경
            ) {
                Text("인증번호 확인", color = Color.White)
            }
        }
    }
}
