package com.project.givuandtake.feature.mypage.MyActivities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.givuandtake.R
import com.project.givuandtake.core.apis.UserInfoResponse

@Composable
fun UserInfoUpdate(navController: NavController, userInfo: UserInfoResponse?) {
    val nameState = remember { mutableStateOf(TextFieldValue(userInfo?.data?.name ?: "")) }
    val emailState = remember { mutableStateOf(TextFieldValue(userInfo?.data?.email ?: "")) }
    val phoneState = remember { mutableStateOf(TextFieldValue(userInfo?.data?.mobilePhone ?: "")) }
    val birthState = remember { mutableStateOf(TextFieldValue(userInfo?.data?.birth ?: "")) }

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
                    .weight(0.3f)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "회원정보 수정",
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 이미지 (기본 이미지 또는 API에서 받은 URL)
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

        Spacer(modifier = Modifier.height(16.dp))

        // 수정 가능한 정보 입력 필드들 (Custom Box로 스타일링)
        EditableTextField("이름", nameState.value.text) { nameState.value = TextFieldValue(it) }
        DrawLine()

        // 이메일 (수정 불가)
        EditableTextField("이메일", emailState.value.text) { emailState.value = TextFieldValue(it) }
        DrawLine()

        // 수정 가능한 전화번호 필드
        EditableTextField("전화번호", phoneState.value.text) { phoneState.value = TextFieldValue(it) }
        DrawLine()

        // 성별 (수정 불가)
        UserInfoItem(label = "성별", value = if (userInfo?.data?.isMale == true) "남성" else "여성")
        DrawLine()

        // 수정 가능한 생일 필드
        EditableTextField("생일", birthState.value.text) { birthState.value = TextFieldValue(it) }
        DrawLine()

        Spacer(modifier = Modifier.weight(1f))

        // 하단 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xffFBFAFF))
                    .border(1.dp, Color(0XFFA093DE), RoundedCornerShape(20.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable { /* 수정 사항 저장 로직 */ }
            ) {
                Text(text = "수정하기", fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun EditableTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    val textState = remember { mutableStateOf(value) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
        )

        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onValueChange(it) // 변경된 값을 외부로 전달
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )
    }
}
