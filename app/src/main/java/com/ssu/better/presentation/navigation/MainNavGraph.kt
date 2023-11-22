package com.ssu.better.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ssu.better.entity.study.Category
import com.ssu.better.presentation.ui.main.home.HomeScreen
import com.ssu.better.presentation.ui.main.home.SampleScreen
import com.ssu.better.presentation.ui.main.mypage.MyPageScreen
import com.ssu.better.presentation.ui.main.search.SearchDetailScreen
import com.ssu.better.presentation.ui.main.search.SearchScreen
import com.ssu.better.presentation.ui.study.create.CreateStudyScreen
import com.ssu.better.presentation.ui.study.detail.StudyDetailScreen
import com.ssu.better.presentation.ui.study.join.StudyJoinScreen
import com.ssu.better.presentation.ui.study.select_category.SelectCategoryScreen
import com.ssu.better.presentation.ui.task.crate.CreateTaskScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        navigation(route = Screen.Search.route, startDestination = Screen.Search.Main.route) {
            composable(route = Screen.Search.Main.route) {
                SearchScreen(navController)
            }

            composable(
                route = Screen.Search.Detail.route + "?query={query}&category={category}",

                arguments = listOf(
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("category") {
                        type = NavType.StringType
                        defaultValue = Category.ALL.name
                    },
                ),
            ) { navBackStackEntry ->
                SearchDetailScreen(
                    navHostController = navController,
                    query = navBackStackEntry.arguments?.getString("query"),
                    category = Category.valueOf(navBackStackEntry.arguments?.getString("category") ?: Category.ALL.name),
                )
            }
        }

        composable(route = Screen.MyPage.route) {
            MyPageScreen(navController)
        }

        composable(route = Screen.Sample.route) {
            SampleScreen(navController)
        }

        composable(
            route = Screen.CreateStudy.route + "?categoryId={categoryId}",
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
        ) { navBackStackEntry ->
            CreateStudyScreen(
                navHostController = navController,
                categoryId = navBackStackEntry.arguments?.getInt("categoryId") ?: 0,
            )
        }

        composable(route = Screen.SelectCategory.route) {
            SelectCategoryScreen(navController = navController)
        }

        composable(
            route = Screen.StudyJoin.route + "?studyId={studyId}",
            arguments = listOf(
                navArgument("studyId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
        ) { navBackStackEntry ->
            StudyJoinScreen(
                navController = navController,
                studyId = navBackStackEntry.arguments?.getInt("studyId") ?: 0,
            )
        }

        composable(
            route = Screen.StudyDetail.route + "?studyId={studyId}",
            arguments = listOf(
                navArgument("studyId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
        ) { navBackStackEntry ->
            StudyDetailScreen(
                navHostController = navController,
                studyId = navBackStackEntry.arguments?.getInt("studyId") ?: 0,
            )
        }

        composable(route = Screen.CreateTask.route) {
            CreateTaskScreen(navHostController = navController)
        }
    }
}
