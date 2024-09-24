package com.project.givuandtake.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.project.givuandtake.R

@Composable
fun SignupStep3(navController: NavController, signupViewModel: SignupViewModel) {

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
                contentAlignment = Alignment.CenterStart
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

            // OOO 단계, 건너뛰기, 시작하기 버튼을 감싸는 중간 박스
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))  // 둥근 모서리 처리
                    .background(Color.White)  // 흰색 배경
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // OOO 단계를 감싸는 박스
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp) // 중간 박스의 윗부분에 맞춰지도록 패딩 조정

                    ) {

                        // ooo 단계
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))  // 비활성화된 단계 색상
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))  // 비활성화된 단계 색상
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF9874))  // 활성화된 단계 색상
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 맞춤설정 텍스트
                    Text(
                        text = "맞춤설정",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp) // 상하 간격을 위한 패딩
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // 안내 텍스트
                    Text(
                        text = "설문에 참여하시면\n맞춤화된 추천을 받으실 수 있어요",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()  // 텍스트를 박스만큼 크기로 설정
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // 건너뛰기 버튼 (데이터 전송 포함)
                    TextButton(
                        onClick = {
                            // ViewModel에서 데이터 취합
                            val signUpDto = mapOf(
                                "name" to signupViewModel.name,
                                "isMale" to signupViewModel.isMale,
                                "birth" to signupViewModel.birth,  // 적절한 날짜 형식으로 변환 필요
                                "email" to signupViewModel.email,
                                "password" to signupViewModel.password,
                                "mobilePhone" to signupViewModel.mobilePhone,
//                                "landlinePhone" to signupViewModel.landlinePhone,
//                                "profileImageUrl" to signupViewModel.profileImageUrl,
//                                "roles" to signupViewModel.roles,
//                                "isSocial" to signupViewModel.isSocial,
//                                "socialType" to signupViewModel.socialType,
//                                "socialSerialNum" to signupViewModel.socialSerialNum
                            )

                            val addressAddDto = mapOf(
                                "zoneCode" to signupViewModel.zoneCode,
                                "addressName" to signupViewModel.addressName,
                                "address" to signupViewModel.address,
                                "userSelectedType" to signupViewModel.userSelectedType,
                                "roadAddress" to signupViewModel.roadAddress,
                                "jibunAddress" to signupViewModel.jibunAddress,
                                "detailAddress" to signupViewModel.detailAddress,
                                "autoRoadAddress" to signupViewModel.autoRoadAddress,
                                "autoJibunAddress" to signupViewModel.autoJibunAddress,
                                "buildingCode" to signupViewModel.buildingCode,
                                "buildingName" to signupViewModel.buildingName,
                                "isApartment" to signupViewModel.isApartment,
                                "sido" to signupViewModel.sido,
                                "sigungu" to signupViewModel.sigungu,
                                "sigunguCode" to signupViewModel.sigunguCode,
                                "roadNameCode" to signupViewModel.roadNameCode,
                                "bcode" to signupViewModel.bcode,
                                "roadName" to signupViewModel.roadName,
                                "bname" to signupViewModel.bname,
                                "bname1" to signupViewModel.bname1,
//                                "isRepresentative" to signupViewModel.isRepresentative
                            )

                            val requestData = mapOf(
                                "signUpDto" to signUpDto,
                                "addressAddDto" to addressAddDto
                            )

                            // API 호출 예시
                            // sendSignupDataToApi(requestData)

                            navController.navigate("mainpage")
                        },
                        modifier = Modifier.align(Alignment.End) // 오른쪽에 배치
                    ) {
                        Text(
                            text = "건너뛰기",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                    Spacer(modifier = Modifier.height(32.dp))

                    // 모든 입력된 데이터를 ViewModel에서 가져와서 API로 전송
                    Button(
                        onClick = {
                            // ViewModel에서 데이터 취합
                            val signUpDto = mapOf(
                                "name" to signupViewModel.name,
                                "isMale" to signupViewModel.isMale,
                                "birth" to signupViewModel.birth,  // 적절한 날짜 형식으로 변환 필요
                                "email" to signupViewModel.email,
                                "password" to signupViewModel.password,
                                "mobilePhone" to signupViewModel.mobilePhone,
                                "landlinePhone" to signupViewModel.landlinePhone,
                                "profileImageUrl" to signupViewModel.profileImageUrl,
                                "roles" to signupViewModel.roles,
                                "isSocial" to signupViewModel.isSocial,
                                "socialType" to signupViewModel.socialType,
                                "socialSerialNum" to signupViewModel.socialSerialNum
                            )

                            val addressAddDto = mapOf(
                                "zoneCode" to signupViewModel.zoneCode,
                                "addressName" to signupViewModel.addressName,
                                "address" to signupViewModel.address,
                                "userSelectedType" to signupViewModel.userSelectedType,
                                "roadAddress" to signupViewModel.roadAddress,
                                "jibunAddress" to signupViewModel.jibunAddress,
                                "detailAddress" to signupViewModel.detailAddress,
                                "autoRoadAddress" to signupViewModel.autoRoadAddress,
                                "autoJibunAddress" to signupViewModel.autoJibunAddress,
                                "buildingCode" to signupViewModel.buildingCode,
                                "buildingName" to signupViewModel.buildingName,
                                "isApartment" to signupViewModel.isApartment,
                                "sido" to signupViewModel.sido,
                                "sigungu" to signupViewModel.sigungu,
                                "sigunguCode" to signupViewModel.sigunguCode,
                                "roadNameCode" to signupViewModel.roadNameCode,
                                "bcode" to signupViewModel.bcode,
                                "roadName" to signupViewModel.roadName,
                                "bname" to signupViewModel.bname,
                                "bname1" to signupViewModel.bname1,
                                "isRepresentative" to signupViewModel.isRepresentative
                            )

                            val requestData = mapOf(
                                "signUpDto" to signUpDto,
                                "addressAddDto" to addressAddDto
                            )

                            // API 호출 예시
                            // sendSignupDataToApi(requestData)

                            navController.navigate("mainpage")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9874))
                    ) {
                        Text("시작하기", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
