package com.project.givuandtake.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignupStep3(navController: NavController) {
    Column {
        Text("Signup Step 3")
        // Step 3 관련 UI 요소들 추가

        Button(onClick = { navController.navigate("mainpage") }) {
            Text("Finish")
        }
    }
}