package com.ssu.better.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssu.better.ui.theme.main.home.HomeScreen
import com.ssu.better.ui.theme.main.home.SampleScreen
import com.ssu.better.ui.theme.main.mypage.MyPageScreen
import com.ssu.better.ui.theme.main.search.SearchScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(route = Screen.MyPage.route) {
            MyPageScreen(navController)
        }
        composable(route = Screen.Sample.route) {
            SampleScreen(navController)
        }
    }
}
