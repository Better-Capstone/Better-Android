package com.ssu.better.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object OnBoard : Screen("onboard")
    object Home : Screen("home")
    object Search : Screen("search") {
        object Main : Screen("search-main")
        object Detail : Screen("search-detail")
    }
    object MyPage : Screen("mypage")

    object Sample : Screen("sample")

    object CreateStudy : Screen("create-study")

    object SelectCategory : Screen("select-category")

    object StudyDetail: Screen("study-detail")
}
