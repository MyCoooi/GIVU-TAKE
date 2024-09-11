package com.project.givuandtake

import GiftPageDetail
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.givuandtake.FundingCard
import com.example.givuandtake.FundingMainPage
import com.project.givuandtake.auth.LoginScreen
import com.project.givuandtake.feature.funding.navigation.MainFundingCard
import com.project.givuandtake.feature.fundinig.FundingDetailPage
import com.project.givuandtake.feature.gift.mainpage.GiftPage
import com.project.givuandtake.feature.mainpage.MainPage
import com.project.givuandtake.feature.navigation.addGiftPageDetailRoute
import com.project.givuandtake.ui.navbar.BottomNavBar
import com.project.givuandtake.ui.theme.GivuAndTakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GivuAndTakeTheme {
                val navController = rememberNavController() // NavController 생성
                var selectedItem by remember { mutableStateOf(0) } // 선택된 항목 상태 추가

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 화면 상단에 NavHost, 하단에 BottomNavBar 배치
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = "mainpage",
                            modifier = Modifier.weight(1f)
                        ) {
                            // 메인 페이지
                            composable("mainpage") { MainPage(navController) }
                            // 펀딩 페이지
                            composable("funding") { FundingMainPage(navController) }
                            // 펀딩 상세 페이지
                            composable(
                                "funding_detail/{title}/{location}/{startDate}/{endDate}/{nowAmount}/{goalAmount}/{imageUrl}"
                            ) { backStackEntry ->
                                val fundingCard = MainFundingCard(backStackEntry)
                                FundingDetailPage(
                                    fundingCard = fundingCard,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            // 로그인 페이지
                            composable("auth") { LoginScreen() }
                            // 기프트 페이지
                            composable("gift") { GiftPage(navController) }
                            // 기프트 상세 페이지
                            addGiftPageDetailRoute() // 모듈화된 GiftPageDetailRoute 추가
                        }

                        // 하단 네비게이션 바
                        BottomNavBar(navController, selectedItem) { newIndex ->
                            selectedItem = newIndex
                        }
                    }
                }
            }
        }
    }
}