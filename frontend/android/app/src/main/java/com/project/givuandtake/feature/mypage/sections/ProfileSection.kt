package com.project.givuandtake.feature.mypage.sections

import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.project.givuandtake.R
import com.project.givuandtake.core.apis.UserInfoApi
import com.project.givuandtake.core.apis.UserInfoResponse
import com.project.givuandtake.core.datastore.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun ProfileSection() {
    val context = LocalContext.current
    val accessToken = "Bearer ${TokenManager.getAccessToken(context)}"
    var userInfo by remember { mutableStateOf<UserInfoResponse?>(null) }

    LaunchedEffect(Unit) {
        UserInfoApi.api.getUserInfo(accessToken).enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                if (response.isSuccessful) {
                    userInfo = response.body()
                    Log.d("UserInfo", "User Data: ${response.body()}")
                } else {
                    Log.d("UserInfo", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("UserInfo", "API Call Failed: ${t.message}")
            }
        })
    }


    // 프로필과 기부 정보를 포함하는 박스
    Surface(
        shape = RoundedCornerShape(25.dp),
        color = Color(0xFFB3C3F4), // 연한 파란색 배경
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 프로필 정보 (이름만 표시)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp) // 왼쪽에 12.dp padding 추가
            ) {
                val profileImageUrl = userInfo?.data?.profileImageUrl

                if (profileImageUrl != null) {
                    // URL이 있으면 AsyncImage 사용
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "User Profile Image",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape) // 이미지를 원으로 잘라줍니다.
                            .background(Color.LightGray, CircleShape) // 배경색 적용
                            .border(0.5.dp, Color.LightGray, CircleShape), // 테두리 추가
                        contentScale = ContentScale.Crop // 이미지를 원 안에 꽉 차도록 설정
                    )
                } else {
                    // URL이 없으면 Image 사용하여 로컬 리소스를 불러옵니다
                    Image(
                        painter = painterResource(id = R.drawable.hamo),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape) // 이미지를 원으로 자릅니다
                            .background(Color.LightGray, CircleShape) // 배경색 적용
                            .border(0.5.dp, Color.LightGray, CircleShape), // 테두리 추가
                        contentScale = ContentScale.Crop // 이미지를 원 안에 꽉 차도록 설정
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 프로필 이름
                userInfo?.data?.let {
                    Text(
                        text = it.name,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 기부 요약 정보 (나의 기부액, 참여한 펀딩 수)
            DonationSummaryCard()
        }
    }
}

@Composable
fun DonationSummaryCard() {
    // 기부 요약 정보 카드
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0XFFFBFAFF), // 흰색 배경
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝으로 정렬
            ) {
                Text(
                    text = "나의 기부액",
                    fontSize = 18.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
                Text(
                    text = "999,999원",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // "참여한 펀딩 수"와 "3건" 가로로 나란히 정렬하면서 세로 중앙 맞춤
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // 세로 중앙 정렬
                horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝으로 정렬
            ) {
                Text(
                    text = "참여한 펀딩 수",
                    fontSize = 18.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
                Text(
                    text = "3건",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.CenterVertically) // 세로 중앙 맞춤
                )
            }
        }
    }
}

