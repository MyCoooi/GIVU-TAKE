package com.project.givuandtake

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.givuandtake.feature.gift.mainpage.DonationPage
import com.project.givuandtake.feature.gift.mainpage.DonationPageDetail
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
                    // gift 함수를 사용하여 테마 및 공통 스타일 적용
                    gift {
                        // 네비게이션 호스트 설정
                        NavHost(navController = navController, startDestination = "donation_page") {
                            composable("donation_page") { DonationPage(navController) }
                            composable("donation_page_detail/{itemIndex}") { backStackEntry ->
                                val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toInt() ?: 0
                                DonationPageDetail(itemIndex)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun gift(content: @Composable () -> Unit){
    MaterialTheme {
        Surface {
            content()
        }
    }
}
