package com.project.givuandtake.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignupStep2(navController: NavController) {
    Column {
        Text("Signup Step 2")
        // Step 2 관련 UI 요소들 추가

        Button(onClick = { navController.navigate("signup_step3") }) {
            Text("Next")
        }
    }
}
