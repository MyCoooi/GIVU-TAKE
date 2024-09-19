package com.project.givuandtake

import AttractionMain
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.givuandtake.FundingMainPage
import com.project.givuandtake.auth.LoginScreen
import com.project.givuandtake.auth.SignupStep1
import com.project.givuandtake.auth.SignupStep2
import com.project.givuandtake.auth.SignupStep3
import com.project.givuandtake.core.data.CartItem
import com.project.givuandtake.feature.attraction.LocationSelect
import com.project.givuandtake.feature.funding.navigation.MainFundingCard
import com.project.givuandtake.feature.fundinig.FundingDetailPage
import com.project.givuandtake.feature.gift.CartPage
import com.project.givuandtake.feature.gift.mainpage.GiftPage
import com.project.givuandtake.feature.mainpage.MainPage
import com.project.givuandtake.feature.mypage.ContributorScreen
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
                val cartItems = remember { mutableStateOf(listOf<CartItem>()) } // 장바구니 상태

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 화면 상단에 NavHost, 하단에 BottomNavBar 배치
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = "auth",
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
                            composable("attraction") { AttractionMain(navController, "") } // Navigate to AttractionMain
                            // 로그인 페이지
                            composable("auth") { LoginScreen(navController) }
                            // 회원가입 페이지
                            composable("signup_step1") { SignupStep1(navController) }
                            composable("signup_step2") { SignupStep2(navController) }
                            composable("signup_step3") { SignupStep3(navController) }
                            // 기프트 페이지
                            composable("gift") {
                                GiftPage(navController = navController) // cartItems는 MutableState로 전달
                            }

                            // 기프트 상세 페이지
                            addGiftPageDetailRoute(navController, cartItems) // cartItems는 MutableState로 전달

                            // 장바구니 페이지
                            composable("cart_page") {
                                CartPage(navController, cartItems) // cartItems.value로 리스트 전달
                            }
                            // 마이 페이지
                            composable("mypage") { ContributorScreen() }
                            composable("locationSelection") {
                                LocationSelect(navController)
                            }
                            composable("attraction?city={city}") { backStackEntry ->
                                val city = backStackEntry.arguments?.getString("city") ?: "지역 선택"
                                AttractionMain(navController, city)
                            }
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