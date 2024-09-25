package com.project.givuandtake.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.givuandtake.feature.mypage.sections.AnnouncementSection
import com.project.givuandtake.feature.mypage.sections.ProfileSection
import com.project.givuandtake.feature.mypage.sections.SectionWithMyActivities
import com.project.givuandtake.feature.mypage.sections.SectionWithMyDonation
import com.project.givuandtake.feature.mypage.sections.SectionWithMyManagement
import com.project.givuandtake.feature.mypage.sections.SectionWithCustomerService

import com.project.givuandtake.feature.mypage.sections.Shortcut


@Composable
fun MyPageScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFFBFAFF)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()) // 스크롤 가능하도록 설정
//                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
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
            Shortcut(navController)
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                color = Color(0xFFF2F2F2), // Set the line color to gray
                thickness = 15.dp, // Set the thickness of the line
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 나의 기부 섹션
            Spacer(modifier = Modifier.height(12.dp))
            SectionWithMyDonation("나의 기부", listOf("기부내역", "찜 목록", "펀딩내역", "기부 영수증"), navController)
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                color = Color(0xFFF2F2F2), // Set the line color to gray
                thickness = 15.dp, // Set the thickness of the line
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 활동 내역 섹션
            Spacer(modifier = Modifier.height(12.dp))
            SectionWithMyActivities("활동 내역", listOf("나의 댓글", "나의 후기"), navController)
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                color = Color(0xFFF2F2F2), // Set the line color to gray
                thickness = 15.dp, // Set the thickness of the line
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 관리 섹션
            Spacer(modifier = Modifier.height(12.dp))
            SectionWithMyManagement("관리", listOf("주소록", "카드", "회원정보"), navController)
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                color = Color(0xFFF2F2F2), // Set the line color to gray
                thickness = 15.dp, // Set the thickness of the line
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 고객센터 섹션
            SectionWithCustomerService("고객센터", navController)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "       로그아웃",
                color = Color.Red, // 빨간색 적용
                fontWeight = FontWeight.Bold, // 굵은 글꼴
                fontSize = 16.sp // 크기 (선택 사항, 필요에 따라 변경 가능)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}





