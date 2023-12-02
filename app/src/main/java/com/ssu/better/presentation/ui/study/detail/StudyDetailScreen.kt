package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toLocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun StudyDetailScreen(
    navHostController: NavHostController,
    studyId: Int,
    viewModel: StudyDetailViewModel = hiltViewModel(),
) {
    val studyEvent = viewModel.studyEventStateFlow.collectAsState()
    viewModel.setStudyId(studyId)
    StudyDetailContent(
        onClickFinish = {},
        studyEvent = studyEvent.value,
        onClickAdd = {
//            navHostController.navigate(Screen.CreateChallenge.route + "?studyId=2&taskId=4")
        },
    )
}

@Composable
@Preview
fun StudyDetailPreview() {
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testMember = Member(1, 1, MemberType.MEMBER, "")
    val testTaskGroup = TaskGroup(
        taskGroupId = 1,
        status = Status.INPROGRESS,
        startDate = "",
        endDate = time,
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
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val tasks = ArrayList(List(2) { testTask }.toMutableList())
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "알고리즘 스터디",
        "설명",
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
    StudyDetailContent(
        onClickFinish = { },
        studyEvent = StudyDetailViewModel.StudyEvent.Success(testStudy, tasks),
        onClickAdd = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyDetailContent(
    onClickFinish: () -> Unit,
    studyEvent: StudyDetailViewModel.StudyEvent,
    onClickAdd: (Study) -> Unit,
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.tab_home),
        stringResource(id = R.string.challenge),
        stringResource(id = R.string.study),
    )

    when (studyEvent) {
        is StudyDetailViewModel.StudyEvent.Load -> {
            ShowLoadingAnimation()
        }

        is StudyDetailViewModel.StudyEvent.Success -> {
            Scaffold(
                modifier = Modifier.background(color = BetterColors.Bg),
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .background(color = BetterColors.Bg)
                            .shadow(elevation = 3.dp),
                        title = {
                            Text(
                                text = studyEvent.study.title,
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
                Column(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .background(color = BetterColors.Bg),
                ) {
                    TabRow(
                        selectedTabIndex = tabIndex,
                        indicator = { tabPositions ->
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[tabIndex])
                                    .height(4.dp)
                                    .background(color = BetterColors.Primary50),
                            )
                        },
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = tabIndex == index,
                                selectedContentColor = BetterColors.Primary50,
                                unselectedContentColor = BetterColors.Gray30,
                                onClick = { tabIndex = index },
                            )
                        }
                    }
                    when (tabIndex) {
                        0 -> StudyHomeScreen(
                            study = studyEvent.study,
                            taskList = studyEvent.taskList,
                            onClickAdd = onClickAdd,
                        )

                        1 -> StudyChallengeScreen(study = studyEvent.study)
                        2 -> StudyInfoScreen(study = studyEvent.study)
                    }
                }
            }
        }
    }
}
