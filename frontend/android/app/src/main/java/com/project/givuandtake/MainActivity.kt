package com.project.givuandtake

import GiftPageDetail
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.givuandtake.FundingMainPage
import com.project.givuandtake.feature.gift.mainpage.GiftPage
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
                        composable("gift") { GiftPage(navController) }
                        composable(
                            "gift_page_detail/{id}/{name}/{price}/{imageUrl}/{location}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType },
                                navArgument("name") { type = NavType.StringType },
                                navArgument("price") { type = NavType.IntType },
                                navArgument("imageUrl") { type = NavType.StringType },
                                navArgument("location") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            // 전달된 인자를 받아서 사용
                            val id = backStackEntry.arguments?.getInt("id") ?: 0
                            val name = backStackEntry.arguments?.getString("name") ?: ""
                            val price = backStackEntry.arguments?.getInt("price") ?: 0
                            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                            val location = backStackEntry.arguments?.getString("location") ?: ""

                            GiftPageDetail(id, name, price, imageUrl, location)
                        }
                    }
                }
            }
        }
    }
}

