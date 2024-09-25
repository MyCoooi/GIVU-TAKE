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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.givuandtake.FundingMainPage
import com.project.givuandtake.auth.LoginScreen
import com.project.givuandtake.auth.SignupStep1
import com.project.givuandtake.auth.SignupStep2
import com.project.givuandtake.auth.SignupStep3
import com.project.givuandtake.core.data.CartItem
import com.project.givuandtake.feature.attraction.FestivalPage
import com.project.givuandtake.feature.attraction.LocationSelect
import com.project.givuandtake.feature.attraction.TripPage
import com.project.givuandtake.feature.funding.navigation.MainFundingCard
import com.project.givuandtake.feature.fundinig.FundingDetailPage
import com.project.givuandtake.feature.gift.CartPage
import com.project.givuandtake.feature.gift.mainpage.GiftPage
import com.project.givuandtake.feature.mainpage.MainPage
import com.project.givuandtake.feature.mypage.MyPageScreen
import com.project.givuandtake.feature.mypage.CustomerService.FaqPage
import com.project.givuandtake.feature.mypage.CustomerService.PersonalInquiry
import com.project.givuandtake.feature.mypage.CustomerService.Announcement
import com.project.givuandtake.feature.mypage.MyActivities.AddressBook
import com.project.givuandtake.feature.mypage.MyActivities.AddressMapSearch
import com.project.givuandtake.feature.mypage.MyActivities.AddressSearch
import com.project.givuandtake.feature.mypage.MyActivities.CardBook
import com.project.givuandtake.feature.mypage.MyActivities.UserInfo
import com.project.givuandtake.feature.mypage.MyDonation.DonationDetails
import com.project.givuandtake.feature.mypage.MyDonation.DonationReceipt
import com.project.givuandtake.feature.mypage.MyDonation.FundingDetails
import com.project.givuandtake.feature.mypage.MyDonation.WishList
import com.project.givuandtake.feature.mypage.MyManagement.MyComment
import com.project.givuandtake.feature.mypage.MyManagement.MyReview
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
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination?.route
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
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("attraction") { AttractionMain(navController, "영도") } // Navigate to AttractionMain
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
                                val context = LocalContext.current // LocalContext를 사용하여 Context 가져오기
                                CartPage(navController = navController, context = context) // context 전달
                            }

                            composable("locationSelection") {
                                LocationSelect(navController)
                            }
                            composable("attraction?city={city}") { backStackEntry ->
                                val city = backStackEntry.arguments?.getString("city") ?: "도 선택"
                                AttractionMain(navController, city)
                            }
                            composable(
                                route = "trippage?city={city}", // Define the route with a city argument
                                arguments = listOf(navArgument("city") { type = NavType.StringType }) // Declare argument type
                            ) { backStackEntry ->
                                // Retrieve the city from the arguments
                                val city = backStackEntry.arguments?.getString("city")
                                TripPage(navController, city) // Pass the city to TripPage
                            }
                            composable(
                                route = "festivalpage?city={city}", // Define the route with a city argument
                                arguments = listOf(navArgument("city") { type = NavType.StringType }) // Declare argument type
                            ) { backStackEntry ->
                                // Retrieve the city from the arguments
                                val city = backStackEntry.arguments?.getString("city")
                                FestivalPage(navController, city) // Pass the city to TripPage
                            }

                            // 마이 페이지
                            composable("mypage") { MyPageScreen(navController) }

                            //마이페이지 세부내역
                            composable("donationdetails") { DonationDetails(navController) }
                            composable("donationreceipt") { DonationReceipt(navController) }
                            composable("fundingdetails") { FundingDetails(navController) }
                            composable("wishlist") { WishList(navController) }

                            composable("mycomment") { MyComment(navController) }
                            composable("myreview") { MyReview(navController) }

                            composable("addressbook") { AddressBook(navController) }
                            composable("cardbook") { CardBook(navController) }
                            composable("userinfo") { UserInfo(navController) }
                            composable("addresssearch") { AddressSearch(navController) }
                            composable("addressmapsearch") { AddressMapSearch(navController)}

                            composable("announcement") { Announcement(navController) }
                            composable("faqpate") { FaqPage(navController) }
                            composable("personalinquiry") { PersonalInquiry(navController) }
                        }

                        // 하단 네비게이션 바
                        if (currentDestination != "trippage?city={city}" && currentDestination != "festivalpage?city={city}") {
                            BottomNavBar(navController, selectedItem) { newIndex ->
                                selectedItem = newIndex
                            }
                        }
                    }
                }
            }
        }
    }
}