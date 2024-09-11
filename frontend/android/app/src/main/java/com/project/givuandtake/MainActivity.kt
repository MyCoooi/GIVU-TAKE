package com.project.givuandtake

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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.givuandtake.FundingCard
import com.example.givuandtake.FundingMainPage
import com.project.givuandtake.feature.fundinig.FundingDetailPage
import com.project.givuandtake.feature.gift.mainpage.GiftPage
import com.project.givuandtake.feature.gift.mainpage.GiftPageDetail
import com.project.givuandtake.feature.mainpage.MainPage
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
                        NavHost(navController = navController, startDestination = "mainpage", modifier = Modifier.weight(1f)) {
                            composable("mainpage") { MainPage(navController) }
                            composable("funding") { FundingMainPage(navController) }
                            composable("funding_detail/{title}/{location}/{startDate}/{endDate}/{nowAmount}/{goalAmount}/{imageUrl}") { backStackEntry ->
                                val fundingCard = MainFundingCard(backStackEntry)
                                FundingDetailPage(
                                    fundingCard = fundingCard,
                                    onBackClick = {
                                        navController.popBackStack()  // 뒤로가기 처리
                                    }
                                )
                            }
                            //composable("attraction") { AttractionPage(navController) }
                            //composable("auth") { AuthPage(navController) }
                            composable("gift") { GiftPage(navController) }
                            composable("gift_page_detail/{itemIndex}") { backStackEntry ->
                                val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toIntOrNull() ?: 0
                                GiftPageDetail(itemIndex)
                            }
                        }
                        BottomNavBar(navController, selectedItem) { newIndex ->
                            selectedItem = newIndex
                        }
                    }
                }
            }
        }
    }

    // MainFundingCard 함수 위치 조정 (NavHost 외부에 위치해야 함)
    fun MainFundingCard(backStackEntry: androidx.navigation.NavBackStackEntry): FundingCard {
        val title = backStackEntry.arguments?.getString("title") ?: ""
        val location = backStackEntry.arguments?.getString("location") ?: ""
        val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
        val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
        val nowAmount = backStackEntry.arguments?.getString("nowAmount")?.toFloatOrNull() ?: 0f
        val goalAmount = backStackEntry.arguments?.getString("goalAmount")?.toFloatOrNull() ?: 0f
        val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""

        return FundingCard(
            title = title,
            location = location,
            startDate = startDate,
            endDate = endDate,
            amounts = Pair(nowAmount, goalAmount),
            imageUrl = imageUrl
        )
    }
}

@Composable
fun BottomNavBar(navController: NavController, selectedItem: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,  // 네비게이션 바 배경 색상
        contentColor = MaterialTheme.colorScheme.onPrimary    // 기본 아이템 텍스트 및 아이콘 색상
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            label = { Text("기부") },
            selected = selectedItem == 0,
            onClick = {
                onItemSelected(0)
                navController.navigate("gift") // 펀딩 페이지로 이동
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            label = { Text("관광") },
            selected = selectedItem == 1,
            onClick = {
                onItemSelected(1)
                navController.navigate("mainpage")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("메인") },
            selected = selectedItem == 2,
            onClick = {
                onItemSelected(2)
                navController.navigate("mainpage")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("펀딩") },
            selected = selectedItem == 3,
            onClick = {
                onItemSelected(3)
                navController.navigate("funding")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("마이페이지") },
            selected = selectedItem == 4,
            onClick = {
                onItemSelected(4)
                navController.navigate("mainpage")
            }
        )
    }
}
