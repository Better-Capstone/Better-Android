package com.ssu.better.ui.theme.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ssu.better.presentation.navigation.Screen

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Column {
        Text(text = "home")
        Button(
            onClick = {
                navHostController.navigate(Screen.Sample.route)
            },
        ) {
            Text(text = "navigate to bottom invisible screen")
        }
    }
}
