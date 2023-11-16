package com.ssu.better.presentation.ui.main.home

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
                navHostController.navigate(Screen.CreateStudy.route)
            },
        ) {
            Text(text = "스터디 추가")
        }
    }
}
