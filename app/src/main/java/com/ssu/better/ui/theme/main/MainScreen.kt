package com.ssu.better.ui.theme.main

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssu.better.presentation.navigation.BetterBottomBar
import com.ssu.better.presentation.navigation.MainNavGraph
import com.ssu.better.presentation.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val DefaultScreen = Screen.Main.Home

    Scaffold(
        bottomBar = {
            if (getBottomVisible(currentRoute(navController) ?: DefaultScreen.route)) BetterBottomBar(navController)
        },

    ) {
        MainNavGraph(navController = navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun getBottomVisible(
    root: String,
): Boolean {
    return when (root) {
        Screen.Main.Home.route -> true
        Screen.Main.Search.route -> true
        Screen.Main.MyPage.route -> true
        else -> false
    }
}
