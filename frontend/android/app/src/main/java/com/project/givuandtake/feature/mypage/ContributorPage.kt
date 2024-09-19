package com.project.givuandtake.feature.mypage

import android.os.Bundle
import androidx.compose.ui.res.painterResource
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.givuandtake.R

class ContributorPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContributorScreen()
        }
    }
}

@Composable
fun ContributorScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFF7F9FC) // 밝은 그레이 톤 배경
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()) // 스크롤 가능하도록 설정
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 프로필 섹션
            ProfileSection()

            Spacer(modifier = Modifier.height(24.dp))


            // 공지사항 섹션
            AnnouncementSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 바로가기(Shortcut) 섹션 추가
            Shortcut()
            Spacer(modifier = Modifier.height(24.dp))

            // 나의 기부 섹션
            SectionWithBackground("나의 기부", listOf("기부내역", "찜 목록", "펀딩내역", "기부 영수증"))

            Spacer(modifier = Modifier.height(24.dp))

            // 활동 내역 섹션
            SectionWithBackground("활동 내역", listOf("나의 댓글", "나의 배지"))

            Spacer(modifier = Modifier.height(24.dp))

            // 관리 섹션
            SectionWithBackground("관리", listOf("주소록", "카드", "회원정보"))

            Spacer(modifier = Modifier.height(24.dp))

            // 고객센터 섹션
            CustomerServiceTitle("고객센터")
            CustomerServiceSection()

            Spacer(modifier = Modifier.height(12.dp))

            Logout()

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun ProfileSection() {
    // 프로필과 기부 정보를 포함하는 박스
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFB3C3F4), // 연한 파란색 배경
        modifier = Modifier
            .fillMaxWidth()
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
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "프로필",
                    modifier = Modifier.size(50.dp),
                    tint = Color(0xFFFFFFFF) // 진한 회색
                )

                Spacer(modifier = Modifier.width(16.dp))

                // 프로필 이름
                Text(
                    text = "김프로님",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFFFF)
                )
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
        shape = RoundedCornerShape(12.dp),
        color = Color(0XFFFBFAFF), // 흰색 배경
        modifier = Modifier
            .fillMaxWidth() // 상자 너비 맞춤
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
        ) {
            // "나의 기부액"과 "999,999원" 가로로 나란히 정렬하면서 세로 중앙 맞춤
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // 세로 중앙 정렬
                horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝으로 정렬
            ) {
                Text(
                    text = "나의 기부액",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
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
                    fontWeight = FontWeight.Bold,
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



@Composable
fun AnnouncementSection() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,  // 흰색 배경
        border = BorderStroke(3.dp, Color(0xFFB3C3F4)), // 테두리 추가
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "공지사항",
                modifier = Modifier.size(24.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "공지사항 - 폭우로 인한 배송 지연",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
        }
    }
}

@Composable
fun SectionWithBackground(title: String, actions: List<String>) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFECECEC).copy(alpha = 0.3f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp, start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        ) {
            // 섹션 제목
            SectionTitle(title)

            // 줄 추가 (Divider)
            Divider(
                color = Color(0XFFB3C3F4), // 줄 색상
                thickness = 2.dp,  // 줄 두께
                modifier = Modifier.padding(vertical = 8.dp)  // 줄의 상하 여백
            )

            // 첫 번째 줄
            if (actions.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = actions.getOrNull(0) ?: "",  // 기부내역
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = actions.getOrNull(1) ?: "",  // 찜 목록
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier.fillMaxWidth(0.5f)  // 너비의 절반에서 시작
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 두 번째 줄
            if (actions.size > 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = actions.getOrNull(2) ?: "",  // 펀딩내역
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = actions.getOrNull(3) ?: "",  // 기부 영수증
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier.fillMaxWidth(0.5f)  // 너비의 절반에서 시작
                    )
                }
            }
        }
    }
}



@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp),
        color = Color(0XFFA093DE)
    )
}
@Composable
fun Shortcut() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0XFFFFFFFF), // 흰색 배경
        modifier = Modifier
            .fillMaxWidth() // 상자 너비 맞춤
            .padding(horizontal = 8.dp) // 좌우에 약간의 패딩 추가
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // 아이콘 간 간격
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShortcutItem(imageRes = R.drawable.donation, text = "기부내역")
            ShortcutItem(imageRes = R.drawable.likes, text = "찜 목록")
            ShortcutItem(imageRes = R.drawable.creditcard, text = "카드")
            ShortcutItem(imageRes = R.drawable.address, text = "주소록")
        }
    }
}

@Composable
fun ShortcutItem(@DrawableRes imageRes: Int, text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally, // 아이콘과 텍스트를 중앙에 정렬
        verticalArrangement = Arrangement.Center
    ) {
        // 로컬 리소스 이미지 로드
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = text,
            modifier = Modifier.size(40.dp) // 이미지 크기 설정
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
    }
}

@Composable
fun CustomerServiceTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp), // 고객센터 제목에만 패딩 적용
        color = Color(0XFFA093DE)
    )
}


@Composable
fun CustomerServiceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CustomerServiceItem("공지사항", Icons.Default.Info) // 공지사항
        Spacer(modifier = Modifier.height(12.dp))
        CustomerServiceItem("1:1 문의", Icons.Default.Email) // 1:1 문의
        Spacer(modifier = Modifier.height(12.dp))
        CustomerServiceItem("자주 묻는 질문", Icons.AutoMirrored.Default.ExitToApp) // 자주 묻는 질문

        Spacer(modifier = Modifier.height(12.dp))

        // 시간 정보
        Text(
            text = "운영시간 : 평일 09:00 ~ 18:00\n(점심시간 12:00 ~ 13:00 제외)",
            fontSize = 14.sp,
            color = Color(0xFF888888),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
    }
}


@Composable
fun CustomerServiceItem(title: String, icon: ImageVector) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,  // 배경을 흰색으로 설정
        border = BorderStroke(3.dp, Color(0xFFB3C3F4)), // 테두리 색상과 두께 설정
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB3C3F4) // 아이콘 색상 설정
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}

@Composable
fun Logout() {
    Text(
        text = "로그아웃",
        color = Color.Red, // 빨간색 적용
        fontWeight = FontWeight.Bold, // 굵은 글꼴
        fontSize = 16.sp // 크기 (선택 사항, 필요에 따라 변경 가능)
    )
}