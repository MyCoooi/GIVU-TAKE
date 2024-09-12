package com.project.givuandtake.feature.mypage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

            // 기부 정보 카드
            DonationSummaryCard()

            Spacer(modifier = Modifier.height(16.dp))

            // 공지사항 섹션
            AnnouncementSection()

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
            SectionTitle("고객센터")
            CustomerServiceSection()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "프로필",
            modifier = Modifier.size(60.dp),
            tint = Color(0xFF333333) // 라이트 블루 색상
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = "김수현님", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}

@Composable
fun DonationSummaryCard() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0XFFFFFFFF),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "나의 기부액", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                Text(text = "999,999원", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "참여한 펀딩 수", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                Text(text = "3건", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
            }
        }
    }
}

@Composable
fun AnnouncementSection() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFDECEA),  // 연한 빨간색 배경
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
                imageVector = Icons.Default.Info,
                contentDescription = "공지사항",
                modifier = Modifier.size(24.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "공지사항 - 폭우로 인한 배송 지연", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}

@Composable
fun SectionWithBackground(title: String, actions: List<String>) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFECECEC).copy(alpha = 0.5f),  // 80% 투명도 회색 배경
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            SectionTitle(title)
            ActionGrid(actions)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = Color.Black
    )
}

@Composable
fun ActionCard(title: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = modifier
            .padding(4.dp),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}


@Composable
fun ActionGrid(actions: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        actions.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    ActionCard(item, modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CustomerServiceSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomerServiceItem("공지사항", Icons.Default.Info)
        Spacer(modifier = Modifier.height(12.dp))  // 아이템 사이 간격
        CustomerServiceItem("1:1 문의", Icons.Default.Email)
        Spacer(modifier = Modifier.height(12.dp))  // 아이템 사이 간격
        CustomerServiceItem("자주 묻는 질문", Icons.Default.CheckCircle)
    }
}

@Composable
fun CustomerServiceItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF1F3F4),
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
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        }
    }
}
