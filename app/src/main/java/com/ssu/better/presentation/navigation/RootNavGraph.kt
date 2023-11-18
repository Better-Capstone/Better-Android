package com.ssu.better.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ssu.better.entity.study.Category
import com.ssu.better.presentation.ui.login.LoginScreen
import com.ssu.better.presentation.ui.main.MainScreen
import com.ssu.better.presentation.ui.onboard.OnBoardScreen
import com.ssu.better.presentation.ui.splash.SplashScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(
            route = Screen.OnBoard.route,
            arguments = listOf(
                navArgument("token") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("nickname") {
                    type = NavType.StringType
                    defaultValue = Category.ALL.name
                },
            ),
        ) {
                navBackStackEntry ->
            OnBoardScreen(
                navController,
                nickname = navBackStackEntry.arguments?.getString("nickname", "") ?: "",
                token = navBackStackEntry.arguments?.getString("token", "") ?: "",
            )
        }
        composable(route = Screen.Home.route) {
            MainScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
