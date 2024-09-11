package com.project.givuandtake.feature.navigation

import GiftPageDetail
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


fun NavGraphBuilder.addGiftPageDetailRoute() {
    composable(
        route = "gift_page_detail/{id}/{name}/{price}/{imageUrl}/{location}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("name") { type = NavType.StringType },
            navArgument("price") { type = NavType.IntType },
            navArgument("imageUrl") { type = NavType.StringType },
            navArgument("location") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val price = backStackEntry.arguments?.getInt("price") ?: 0
        val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
        val location = backStackEntry.arguments?.getString("location") ?: ""

        GiftPageDetail(id, name, price, imageUrl, location)
    }
}