package com.ssu.better.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            route = Screen.OnBoard.route + OnBoardNavArgument.QUERY,
            arguments = listOf(
                navArgument(OnBoardNavArgument.TOKEN) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(OnBoardNavArgument.NICKNAME) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { navBackStackEntry ->
            OnBoardScreen(
                navController,
                nickname = navBackStackEntry.arguments?.getString(OnBoardNavArgument.NICKNAME, "") ?: "",
                token = navBackStackEntry.arguments?.getString(OnBoardNavArgument.TOKEN, "") ?: "",
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
