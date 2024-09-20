package com.project.givuandtake.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.givuandtake.feature.mypage.sections.AnnouncementSection
import com.project.givuandtake.feature.mypage.sections.CustomerServiceSection
import com.project.givuandtake.feature.mypage.sections.CustomerServiceTitle
import com.project.givuandtake.feature.mypage.sections.Logout
import com.project.givuandtake.feature.mypage.sections.ProfileSection
import com.project.givuandtake.feature.mypage.sections.SectionWithBackground
import com.project.givuandtake.feature.mypage.sections.Shortcut


@Composable
fun ContributorScreen(navController: NavController) {
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
            CustomerServiceSection(navController = navController)
            Spacer(modifier = Modifier.height(12.dp))

            Logout()
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}





