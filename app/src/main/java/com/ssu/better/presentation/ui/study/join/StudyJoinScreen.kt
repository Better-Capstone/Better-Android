package com.ssu.better.presentation.ui.study.join

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssu.better.R
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.state.IdleEvent
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyJoinScreen(
    navController: NavController,
    studyId: Int,
    viewModel: StudyJoinViewModel = hiltViewModel(),
) {
    viewModel.setStudyId(studyId)
    val uiState by viewModel.uiState.collectAsState()
    val idleEvent by viewModel.idleEvent.collectAsState()
    StudyJoinContent(
        uiState = uiState,
        idleEvent = idleEvent,
        onClickJoinButton = viewModel::join,
        onClickFinish = {
            navController.popBackStack()
        },
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewStudyJoin() {
    val testUser = User(1, "배현빈", "개발하는 북극곰")
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
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val testUserRank = UserRank(
        id = 3,
        user = testUser,
        score = 7530,
        createdAt = "",
        updatedAt = "",
        userRankHistoryList = arrayListOf(testUserRankHistory),
    )
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "알고리즘 스터디",
        "스터디 설명",
        Status.INPROGRESS,
        StudyPeriod.EVERYDAY,
        StudyCheckDay.EVERYDAY,
        5,
        1,
        10,
        1500,
        arrayListOf(testMember),
        userRankHistoryList = arrayListOf(testUserRankHistory),
        groupRank = testGroupRank,
        createdAt = "",
        taskGroupList = arrayListOf(),
    )
    val testStudyUser = StudyUser(1, "배현빈", "개발하는 북극곰", testUserRank, arrayListOf(testMember), arrayListOf(testStudy), "", "")

    StudyJoin(
        study = testStudy,
        userList = arrayListOf(testStudyUser),
        onClickFinish = { },
        onClickJoinButton = { },
        userId = 0,
    )
}

@Composable
fun StudyJoinContent(
    uiState: StudyJoinViewModel.UIState,
    idleEvent: IdleEvent,
    onClickJoinButton: () -> Unit,
    onClickFinish: () -> Unit,
) {
    LaunchedEffect(idleEvent) {
        if (idleEvent is IdleEvent.Finish) {
            onClickFinish()
        }
    }
    when (uiState) {
        is StudyJoinViewModel.UIState.Success -> {
            StudyJoin(
                study = uiState.study,
                userList = uiState.userList,
                userId = uiState.userPref.id,
                onClickFinish = onClickFinish,
                onClickJoinButton = onClickJoinButton,
            )
        }

        is StudyJoinViewModel.UIState.Loading -> {
            ShowLoadingAnimation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyJoin(
    study: Study,
    userList: ArrayList<StudyUser>,
    userId: Long,
    onClickFinish: () -> Unit,
    onClickJoinButton: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = "참여하기",
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            item(span = { GridItemSpan(4) }) {
                Column {
                    Row(
                        modifier = Modifier.padding(top = 22.dp, start = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = when (study.category.name) {
                                Category.IT.name -> painterResource(id = R.drawable.ic_category_it)

                                Category.ART.name -> painterResource(id = R.drawable.ic_category_art)

                                Category.BUSINESS.name -> painterResource(id = R.drawable.ic_category_business)

                                Category.CERTIFICATE.name -> painterResource(id = R.drawable.ic_category_certificate)

                                Category.HEALTH.name -> painterResource(id = R.drawable.ic_category_health)

                                else -> painterResource(id = R.drawable.ic_category_language)
                            },
                            contentDescription = "카테고리 아이콘",
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = study.title,
                            style = BetterAndroidTheme.typography.headline2,
                            color = BetterColors.Black,
                        )
                    }

                    Text(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        text = "스터디 설명",
                        style = BetterAndroidTheme.typography.subtitle,
                    )

                    Text(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        text = study.description,
                        style = BetterAndroidTheme.typography.body,
                        color = BetterColors.DescriptionGray,
                    )

                    Row(modifier = Modifier.padding(start = 20.dp, top = 20.dp)) {
                        Text(
                            text = "스터디 주기",
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Black,
                        )

                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = when (study.period) {
                                StudyPeriod.EVERYDAY -> "매일"
                                StudyPeriod.WEEKLY -> "매주"
                                else -> "격주"
                            },
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Primary50,
                        )
                    }

                    Text(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        text = "스터디 반복 요일",
                        style = BetterAndroidTheme.typography.subtitle,
                    )

                    StudyCheckDayCard(study.checkDay, study.period)

                    Row(modifier = Modifier.padding(start = 20.dp, top = 30.dp)) {
                        Text(
                            text = "자동 퇴출 조건",
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Black,
                        )

                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "${study.kickCondition}회 결석 시",
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Primary50,
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            items(userList.size) { index ->
                val item = userList[index]
                StudyUserBox(item)
            }

            item(span = { GridItemSpan(4) }) {
                Column {
                    Spacer(modifier = Modifier.height(74.dp))

                    BetterButton(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                        text = "참여하기",
                        type = BetterButtonType.DEFAULT,
                        onClick = onClickJoinButton,
                        enabled = userList.none { it.userId == userId },
                    )
                }
            }
        }
    }
}
