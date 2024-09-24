package com.project.givuandtake.auth

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.project.givuandtake.R
import kotlinx.coroutines.launch

@Composable
fun WebViewScreen(onAddressSelected: (String, String) -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        // 상단 TopBar 추가
        TopAppBar(
            title = {
                Text(text = "우편번호 찾기", fontSize = 18.sp)
            },
            actions = {
                IconButton(onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기"
                    )
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black,
            elevation = 4.dp
        )

        // WebView를 표시
        AndroidView(factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true  // 자바스크립트 활성화
                settings.domStorageEnabled = true  // DOM 스토리지 활성화
                webViewClient = WebViewClient()

                // 자바스크립트 인터페이스 추가
                addJavascriptInterface(WebAppInterface(onAddressSelected), "Android")

                // 로컬 assets에 있는 daum_address.html 파일 로드
                loadUrl("https://searchaddress-dfaca.web.app/daum_address.html")
            }
        }, modifier = Modifier.fillMaxSize())
    }
}



class WebAppInterface(private val onAddressSelected: (String, String) -> Unit) {
    @JavascriptInterface
    fun processAddress(address: String, detailAddress: String) {
        Log.d("WebAppInterface", "Received address: $address")  // 전달된 주소 로그로 확인
        onAddressSelected(address, detailAddress)
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupStep2(navController: NavController, signupViewModel: SignupViewModel) {
    var address by remember { mutableStateOf("") } // 주소 선택 결과 저장
    var addressDetail by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf<Boolean?>(null) }
    var addressName by remember { mutableStateOf("") }
    var customAddressInput by remember { mutableStateOf("") }
    var showWebView by remember { mutableStateOf(false) } // WebView 표시 여부

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFD7C4))
        ) {
            if (showWebView) {
                // WebView가 화면 전체를 차지하도록 설정
                WebViewScreen(
                    onAddressSelected = { selectedAddress, _ ->
                        address = selectedAddress // WebView에서 선택된 주소 반영
                        showWebView = false // WebView 닫기
                    },
                    onClose = { showWebView = false } // 닫기 버튼을 눌렀을 때 WebView를 닫음
                )
            } else {
                // 기존 화면 구성
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
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
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = "뒤로가기",
                                    tint = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.weight(0.7f))

                            Text(
                                text = "GIVU & TAKE",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFFFFFF)
                            )

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 회원가입과 입력 필드를 감싸는 박스
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // o o o 단계를 감싸는 박스
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp) // 중간 박스의 윗부분에 맞춰지도록 패딩 조정
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
                                        .background(Color(0xFFFF9874))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE0E0E0))
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

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
                                    .clip(RoundedCornerShape(20.dp))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    // 주소 입력 필드 및 주소찾기 버튼
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        OutlinedTextField(
                                            value = address, // WebView에서 선택된 주소 반영
                                            onValueChange = { address = it },
                                            label = { Text("주소") },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(vertical = 8.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color(0xFFFFA726),
                                                unfocusedBorderColor = Color.Gray
                                            ),
                                            enabled = false // 입력 불가
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))

                                        Button(
                                            onClick = { showWebView = true }, // 주소 찾기 버튼을 클릭하면 WebView 표시
                                            modifier = Modifier
                                                .height(60.dp)
                                                .padding(top = 6.dp)
                                                .clip(RoundedCornerShape(12.dp)),
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Color(0xFFFF9874)
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
                                        value = customAddressInput,
                                        onValueChange = { customAddressInput = it },
                                        label = { Text("직접입력 주소") },
                                        placeholder = { Text("예) 동생집, 이모집") },
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
                                        onClick = { isMale = true },
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
                                        onClick = { isMale = false },
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
                                        backgroundColor = Color(0xFFFF9874)
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
}



