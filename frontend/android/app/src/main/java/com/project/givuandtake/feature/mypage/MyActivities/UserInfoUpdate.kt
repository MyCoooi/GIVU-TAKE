package com.project.givuandtake.feature.mypage.MyActivities

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.givuandtake.R
import com.project.givuandtake.core.apis.UserInfoApi
import com.project.givuandtake.core.apis.UserInfoResponse
import com.project.givuandtake.core.apis.UserUpdateApi
import com.project.givuandtake.core.apis.UserUpdateRequest
import com.project.givuandtake.core.datastore.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun UserInfoUpdate(navController: NavController) {
    // 유저 정보 상태 정의
    var userInfo by remember { mutableStateOf<UserInfoResponse?>(null) }
    var nameState by remember { mutableStateOf(("")) }
    var emailState by remember { mutableStateOf("") }
    var phoneState by remember { mutableStateOf(("")) }
    var birthState by remember { mutableStateOf(("")) }
    var isMaleState by remember { mutableStateOf("") }

    val context = LocalContext.current
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"

    // 모달 상태 관리
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val call = UserInfoApi.api.getUserInfo(accessToken)
        call.enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("UserInfoUpdate", "API 호출 성공: ${response.body()}")
                    userInfo = response.body()
                    userInfo?.let {
                        // API에서 받은 데이터를 상태에 반영
                        nameState = it.data.name
                        emailState = it.data.email
                        phoneState = it.data.mobilePhone
                        birthState = it.data.birth
                        isMaleState = if (it.data.isMale) "남성" else "여성"
                    }
                } else {
                    Log.e("UserInfoUpdate", "API 호출 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("UserInfoUpdate", "API 호출 에러: ${t.message}")
            }
        })
    }

    // UI 구성
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 상단 회원 정보 타이틀과 뒤로가기 버튼
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
            )

            Text(
                text = "회원정보 수정",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 프로필 이미지 표시
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            val profileImageUrl = userInfo?.data?.profileImageUrl
            if (profileImageUrl != null) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "User Profile Image",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray, CircleShape)
                        .border(0.5.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.hamo),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray, CircleShape)
                        .border(0.5.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 회원정보 수정 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // 양쪽 끝으로 배치
        ) {
            // 회원정보 탈퇴 버튼
            Text(
                text = "회원정보 탈퇴",
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { showDeleteDialog = true }  // 클릭 시 탈퇴 알림창 표시
            )

            // 수정하기 버튼
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xffFBFAFF))
                    .border(1.dp, Color(0XFFA093DE), RoundedCornerShape(20.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        // 회원정보 수정 로직 호출
                    }
            ) {
                Text(text = "수정하기", fontSize = 14.sp, color = Color.Black)
            }
        }

        // UserAccountDeleteDialog 호출
        UserAccountDeleteDialog(
            navController = navController,
            showDeleteDialog = showDeleteDialog,
            onDismiss = { showDeleteDialog = false }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}
