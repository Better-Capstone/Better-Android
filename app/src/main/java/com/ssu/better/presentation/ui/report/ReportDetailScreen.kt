package com.ssu.better.presentation.ui.report

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.challenge.ChallengeHistory
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
import com.ssu.better.entity.user.UserScore
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.presentation.component.CircleRankProfile
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toLocalDate
import okhttp3.internal.format
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailScreen(
    navHostController: NavHostController,
    studyId: Long,
    historyId: Long,
    viewModel: ReportDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getGroupRankHistory(studyId, historyId)
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
                    IconButton(onClick = { navHostController.popBackStack() }) {
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
            is ReportDetailUiState.Loading -> {
                ShowLoadingAnimation()
            }

            is ReportDetailUiState.Fail -> {
                ErrorScreen(modifier = Modifier.fillMaxSize(), message = (uiState as ReportDetailUiState.Fail).message)
            }

            is ReportDetailUiState.Success -> {
                with(uiState as ReportDetailUiState.Success) {
                    val study = this.study
                    val history = this.history.sortedByDescending { it.taskGroup.endDate }
                    val curHistory = this.history.first { it.groupRankHistoryId == historyId }
                    val chpater = this.history.indexOfFirst { it.groupRankHistoryId == historyId }
                    val myScore = this.userScore

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding() + 8.dp, bottom = 10.dp)
                            .verticalScroll(scrollState),
                    ) {
                        Text(
                            text = stringResource(id = R.string.report_category_level),
                            textAlign = TextAlign.Left,
                            style = BetterAndroidTheme.typography.headline2,
                            modifier = Modifier.padding(12.dp),
                        )

                        StudyRankingCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 12.dp),
                            study = study,
                            historyId = historyId,
                            history = history,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp, horizontal = 20.dp),
                        ) {
                            ChallengePercent(
                                modifier = Modifier.weight(1f),
                                percent = 100 * curHistory.participantsNumber / curHistory.totalNumber,
                                text = stringResource(id = R.string.report_team_percent),
                                color = BetterColors.Gray90,
                            )
                            ChallengePercent(
                                modifier = Modifier.weight(1f),
                                percent = myScore,
                                text = stringResource(id = R.string.report_my_percent),
                                color = BetterColors.Primary50,
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(0.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(BetterColors.Primary00)
                                    .height(60.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.report_reward),
                                    style = BetterAndroidTheme.typography.subtitle,
                                    color = BetterColors.Black,
                                )
                                Text(
                                    text = " ${viewModel.calculateReward(curHistory, myScore)}",
                                    style = BetterAndroidTheme.typography.subtitle,
                                    color = BetterColors.Primary50,
                                )
                                Text(text = "점", style = BetterAndroidTheme.typography.subtitle, color = BetterColors.Black)
                            }
                        }

                        RankingListView(
                            totalMember = curHistory.totalNumber,
                            challenges = curHistory.challengeList,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StudyRankingCard(
    modifier: Modifier = Modifier,
    study: Study,
    historyId: Long,
    history: List<GroupRankHistory>,
) {
    val endTasks = history.filter { it.taskGroup.status == Status.END }.sortedByDescending { it.taskGroup.endDate }
    val curHistory = history.first { it.groupRankHistoryId == historyId }

    Timber.i(curHistory.toString())

    val growth = if (history.size > 1) {
        val prev = endTasks[1]
        val score = curHistory.score - prev.score
        if (score >= 0) {
            format(stringResource(id = R.string.study_rank_up), score)
        } else {
            format(stringResource(id = R.string.study_rank_down), score)
        }
    } else {
        stringResource(id = R.string.study_rank_none)
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = Category.safeValueOf(study.category.name).kor,
                    color = BetterColors.Primary50,
                    style = BetterAndroidTheme.typography.subtitle,
                )
                Text(text = " 스터디 내 ", color = BetterColors.Gray70, style = BetterAndroidTheme.typography.subtitle)
                Text(text = study.title, color = BetterColors.Gray30, style = BetterAndroidTheme.typography.subtitle)
                Text(text = "는?", color = BetterColors.Gray70, style = BetterAndroidTheme.typography.subtitle)
            }
            Image(
                painter = painterResource(
                    id = when (curHistory.score) {
                        in 0..19 -> {
                            R.drawable.ic_report_level1
                        }

                        in 20..39 -> {
                            R.drawable.ic_report_level2
                        }

                        in 40..59 -> {
                            R.drawable.ic_report_level3
                        }

                        in 60..79 -> {
                            R.drawable.ic_report_level4
                        }

                        else -> {
                            R.drawable.ic_report_level5
                        }
                    },
                ),
                contentDescription = "report group rank",
                modifier = Modifier.width(200.dp),
            )
            Text(text = growth, modifier = Modifier.padding(vertical = 16.dp), style = BetterAndroidTheme.typography.subtitle)
            BetterRoundChip(
                enabled = true,
                text = format(stringResource(id = R.string.study_count), history.size - endTasks.indexOf(curHistory)),
                onClick = {},
            )
        }
    }
}

@Composable
fun ChallengePercent(
    modifier: Modifier = Modifier,
    percent: Int,
    text: String,
    color: Color,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "$percent%", color = color, style = BetterAndroidTheme.typography.headline0)
        Text(text = text, style = BetterAndroidTheme.typography.subtitle, modifier = Modifier.padding(top = 6.dp))
    }
}

@Composable
fun RankingListView(
    totalMember: Int,
    challenges: List<ChallengeHistory>,
    listState: LazyListState = rememberLazyListState(),
) {
    Column(
        Modifier.padding(horizontal = 16.dp, vertical = 30.dp),
    ) {
        Text(
            text = stringResource(id = R.string.report_early_bird),
            textAlign = TextAlign.Left,
            style = BetterAndroidTheme.typography.headline2,
            modifier = Modifier.padding(bottom = 30.dp),
        )
        LazyColumn(
            modifier = Modifier
                .heightIn(min = 200.dp, max = 500.dp)
                .padding(horizontal = 8.dp),
            state = listState,
        ) {
            item {
                challenges.filter { it.approveMember.size >= (totalMember / 2) }.sortedBy { it.createdAt }.forEachIndexed { idx, history ->
                    RankingItem(idx + 1, history.user)
                }
            }
        }
    }
}

@Composable
fun RankingItem(
    rank: Int,
    userScore: UserScore,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$rank",
                color = BetterColors.Primary50,
                style = BetterAndroidTheme.typography.title,
                modifier = Modifier.padding(end = 16.dp),
            )
            CircleRankProfile(score = userScore.score, modifier = Modifier.size(35.dp))
            Text(
                text = userScore.nickname,
                style = BetterAndroidTheme.typography.subtitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BetterColors.Gray00),
        )
    }
}

@Preview
@Composable
fun PreviewReportScreen() {
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

//    ReportDetailScreen(study = testStudy, groupRankHistoryList = listOf(groupRankHistory, groupRankHistory.copy(score = 79)))
}
