package com.project.givuandtake.feature.mainpage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("attraction") }) {
            Text(text = "Attraction")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("auth") }) {
            Text(text = "Auth")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("funding") }) {
            Text(text = "Funding")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("gift") }) {
            Text(text = "Gift")
        }
    }
}
