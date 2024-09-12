package com.project.givuandtake.ui.navbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

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
                navController.navigate("gift") // 기부 페이지로 이동
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
            label = { Text("회원정보") },
            selected = selectedItem == 4,
            onClick = {
                onItemSelected(4)
                navController.navigate("mypage")
            }
        )
    }
}