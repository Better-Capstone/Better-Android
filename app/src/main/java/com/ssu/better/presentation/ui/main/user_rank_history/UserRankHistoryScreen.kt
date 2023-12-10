package com.ssu.better.presentation.ui.main.user_rank_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun UserRankHistoryScreen(
    navController: NavHostController,
    viewModel: UserRankHistoryViewModel = hiltViewModel(),
) {
    val event by viewModel.event.collectAsState()
    UserRankHistoryContent(
        event = event,
        onClickFinish = { navController.popBackStack() },
    )
}

@Preview
@Composable
fun PreviewUserRankHistory() {
    val testMember = Member(1, 1, MemberType.MEMBER, "")
    val testTaskGroup = TaskGroup(
        taskGroupId = 1,
        status = Status.INPROGRESS,
        startDate = "",
        endDate = "",
        createdAt = "",
        updatedAt = "",
    )
    val testTask = Task(
        taskId = 1,
        taskGroup = testTaskGroup,
        member = testMember,
        challenge = null,
        createdAt = "",
        updatedAt = "",
        title = "",
    )
    val testUserRankHistory = UserRankHistory(
        1,
        SimpleStudy(1, "알고리즘 스터디"),
        50,
        "50점 추가",
        testTask,
        "2023-12-04T00:00:02.815615",
        "2023-12-04T00:00:02.815615",
    )

    UserRankHistoryContent(
        event = UserRankHistoryViewModel.UserRankEvent.Success(listOf(testUserRankHistory, testUserRankHistory)),
        onClickFinish = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRankHistoryContent(
    event: UserRankHistoryViewModel.UserRankEvent,
    onClickFinish: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(color = BetterColors.Bg),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = BetterColors.Bg)
                    .shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = "적립 내역",
                        style = BetterAndroidTheme.typography.subtitle,
                        color = BetterColors.Black,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClickFinish) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (event) {
                is UserRankHistoryViewModel.UserRankEvent.Load -> {
                    ShowLoadingAnimation()
                }

                is UserRankHistoryViewModel.UserRankEvent.Success -> {
                    LazyColumn {
                        items(event.userRankHistoryList.size) { index ->
                            val item = event.userRankHistoryList[index]
                            Box(modifier = Modifier.padding(10.dp)) {
                                UserRankHistoryCard(userRankHistory = item)
                            }
                        }
                    }
                }
            }
        }
    }
}
