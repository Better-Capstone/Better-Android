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

    object StudyJoin : Screen("study-join")

    object StudyDetail : Screen("study-detail")

    object MemberList : Screen("member-list")

    object CreateTask : Screen("create-task")

    object CreateChallenge : Screen("create-challenge")

    object VerifyChallenge : Screen("verify-challenge")

    object UserRankHistory : Screen("user-rank-history")

    object Report : Screen("report") {
        object ReportList : Screen("report-list")
        object ReportDetail : Screen("report-detail")
    }

    object StudyDetailMy : Screen("study-detail-my")
}
