package com.ssu.better.presentation.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.GroupRankHistory
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.noRippleClickable
import com.ssu.better.util.toLocalDate
import okhttp3.internal.format
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    navController: NavHostController,
    studyId: Long,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getGroupRankHistory(studyId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.report_topbar),
                        style = BetterAndroidTheme.typography.headline3,
                        color = BetterColors.Black,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },

    ) {
        when (uiState) {
            is ReportUiState.Loading -> {
                ShowLoadingAnimation()
            }

            is ReportUiState.Fail -> (ErrorScreen(modifier = Modifier.fillMaxSize(), message = (uiState as ReportUiState.Fail).message))

            is ReportUiState.Success -> {
                val reports = (uiState as ReportUiState.Success).list
                if (reports.isEmpty()) {
                    ErrorScreen(modifier = Modifier.fillMaxSize(), message = stringResource(id = R.string.report_empty))
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .background(BetterColors.Gray00)
                            .padding(top = it.calculateTopPadding() + 20.dp)
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 20.dp)
                            .fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        itemsIndexed(reports) { idx, report ->
                            ReportItem(
                                idx = reports.size - idx,
                                groupRankHistory = report,
                                onClick = {
                                    navController.navigate(
                                        route = Screen.Report.ReportDetail.route + "?studyId=$studyId&&historyId=${report.groupRankHistoryId}",
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReportItem(
    idx: Int,
    groupRankHistory: GroupRankHistory,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .paint(painterResource(id = R.drawable.bg_report), contentScale = ContentScale.FillWidth)
            .padding(vertical = 20.dp)
            .noRippleClickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "${100 * groupRankHistory.participantsNumber / groupRankHistory.totalNumber}%",
            style = BetterAndroidTheme.typography.headline0,
            modifier = Modifier.padding(vertical = 10.dp),

        )
        BetterRoundChip(enabled = true, text = format(stringResource(id = R.string.study_count), idx)) {}
    }
}

@Preview
@Composable
fun PreviewReportDetail() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testMember = Member(1, 1, MemberType.MEMBER, time)
    val testTaskGroup = TaskGroup(
        taskGroupId = 1,
        status = Status.END,
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
        createdAt = time,
        updatedAt = time,
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
    val testGroupRank = GroupRank(1, 80)
    val tasks = List(5) { testTask }.toMutableList()
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
        userRankHistoryList = arrayListOf(testUserRankHistory, testUserRankHistory.copy(score = 67)),
        groupRank = testGroupRank,
        createdAt = "",
        taskGroupList = arrayListOf(),
    )
//    ReportScreen(
//        history = List(10) { groupRankHistory }.mapIndexed { idx, report ->
//            report.copy(totalNumber = (idx + 1) * 2, participantsNumber = (idx) + 2)
//        },
//
//    )
}
