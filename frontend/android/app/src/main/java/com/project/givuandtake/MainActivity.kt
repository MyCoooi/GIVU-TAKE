package com.project.givuandtake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "mainpage") {
                        composable("mainpage") { MainPage(navController) }
//                        composable("attraction") { AttractionPage(navController) }
//                        composable("auth") { AuthPage(navController) }
                        composable("funding") { FundingMainPage(navController) }
                        composable("funding_detail/{title}/{location}/{startDate}/{endDate}/{nowAmount}/{goalAmount}/{imageUrl}") { backStackEntry ->
                            val fundingCard = MainFundingCard(backStackEntry)
                            FundingDetailPage(
                                fundingCard = fundingCard,
                                onBackClick = {
                                    navController.popBackStack()  // This will handle back navigation
                                }
                            )
                        }

                        composable("gift") { GiftPage(navController) }
                        composable("gift_page_detail/{itemIndex}") { backStackEntry ->
                            val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toIntOrNull() ?: 0
                            GiftPageDetail(itemIndex)
                        }
                    }
                }
            }
        }
    }



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


