package com.project.givuandtake.feature.navigation

import GiftPageDetail
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.givuandtake.core.data.CartItem
import com.project.givuandtake.feature.gift.addToCart
import kotlinx.coroutines.launch


fun NavGraphBuilder.addGiftPageDetailRoute(navController: NavController, cartItems: MutableState<List<CartItem>>) {
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

        // Composable에서 필요한 Context 및 CoroutineScope 가져오기
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        GiftPageDetail(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
            location = location,
            cartItems = cartItems, // MutableState<List<CartItem>>를 직접 전달
            onAddToCart = {
                // currentItem을 CartItem으로 생성하여 추가
                val newItem = CartItem(name = name, price = price, quantity = 1, location = location)
                scope.launch {
                    addToCart(context, newItem) // DataStore를 사용하여 장바구니에 아이템 추가
                }
            },
            navController = navController
        )
    }
}



