package com.ssu.better.presentation.ui.main.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.entity.user.UserRankName
import com.ssu.better.presentation.component.MoreButton
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.component.StudyCard
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun MyPageScreen(
    navHostController: NavHostController,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val isNotifyEnabled = viewModel.isNotifyEnabled.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    MyPageContent(
        uiState = uiState.value,
        isNotifyEnabled = isNotifyEnabled.value,
        onClickNotifyChange = viewModel::changeNotify,
        onClickStudy = { study ->
            navHostController.navigate(Screen.StudyDetail.route + "?studyId=${study.studyId}")
        },
        onClickHistory = {
            navHostController.navigate(Screen.UserRankHistory.route)
        },
    )
}

@Composable
fun MyPageContent(
    uiState: MyPageViewModel.UIState,
    isNotifyEnabled: Boolean,
    onClickNotifyChange: (Boolean) -> Unit,
    onClickStudy: (Study) -> Unit,
    onClickHistory: () -> Unit,
) {
    when (uiState) {
        is MyPageViewModel.UIState.Success -> {
            MyPage(
                userRank = uiState.userRank,
                studyList = uiState.studyList,
                isNotifyEnabled = isNotifyEnabled,
                onClickNotifyEnabledChange = onClickNotifyChange,
                onClickLogout = {},
                onClickWithDraw = {},
                onClickStudy = onClickStudy,
                onClickHistory = onClickHistory,
            )
        }

        else -> {
            ShowLoadingAnimation()
        }
    }
}

@Composable
fun MyPage(
    userRank: UserRank,
    studyList: List<Study>,
    isNotifyEnabled: Boolean,
    onClickNotifyEnabledChange: (Boolean) -> Unit,
    onClickLogout: () -> Unit,
    onClickWithDraw: () -> Unit,
    onClickStudy: (Study) -> Unit,
    onClickHistory: () -> Unit,
) {
    Surface(color = BetterColors.Bg) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            UserRankCard(
                userName = userRank.user.nickname,
                userRank = userRank,
                onClickHistory = onClickHistory,
            )

            Text(
                modifier = Modifier.padding(top = 23.dp, start = 20.dp, bottom = 10.dp),
                text = "진행중인 스터디",
                style = BetterAndroidTheme.typography.headline3,
                color = BetterColors.Black,
            )

            LazyRow(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(studyList.size) { index ->
                    val item = studyList[index]
                    StudyCard(
                        modifier = Modifier
                            .width(141.dp).wrapContentHeight(),

                        study = item,
                        onClick = {
                            onClickStudy(item)
                        },
                    )
                }
            }

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
//            ) {
//                Text(
//                    text = "알림 설정",
//                    style = BetterAndroidTheme.typography.headline3,
//                    color = BetterColors.Black,
//                )
//
//                Spacer(Modifier.weight(1f))
//
//                Text(
//                    modifier = Modifier.clickable { onClickNotifyEnabledChange(true) },
//                    text = "ON",
//                    color = if (isNotifyEnabled) BetterColors.Primary50 else BetterColors.Gray20,
//                )
//                Text(
//                    modifier = Modifier
//                        .padding(start = 10.dp)
//                        .clickable { onClickNotifyEnabledChange(false) },
//                    text = "OFF",
//                    color = if (!isNotifyEnabled) BetterColors.Primary50 else BetterColors.Gray20,
//                )
//            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .clickable { onClickLogout() },
                text = "로그아웃",
                style = BetterAndroidTheme.typography.headline3,
                color = BetterColors.Black,
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .clickable { onClickWithDraw() },
                text = "회원탈퇴",
                style = BetterAndroidTheme.typography.headline3,
                color = BetterColors.Primary50,
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewMyPage() {
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
    val testUserRank = UserRank(
        id = 3,
        user = testUser,
        score = 7530,
        createdAt = "",
        updatedAt = "",
        userRankHistoryList = arrayListOf(testUserRankHistory),
    )
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "제목",
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

    val testStudyList = arrayListOf(testStudy, testStudy, testStudy)

    MyPage(
        userRank = testUserRank,
        studyList = testStudyList,
        isNotifyEnabled = true,
        onClickNotifyEnabledChange = { },
        onClickLogout = { },
        onClickWithDraw = { },
        onClickStudy = { },
        onClickHistory = { },
    )
}

@Composable
fun UserRankCard(
    userName: String,
    userRank: UserRank,
    onClickHistory: () -> Unit = { },
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 12.dp),
                    text = "$userName 님의 Better 점수",
                    style = BetterAndroidTheme.typography.headline2,
                )
                Spacer(modifier = Modifier.weight(1f))

                MoreButton(
                    modifier = Modifier.padding(end = 12.dp),
                    text = "적립 내역",
                    onClick = onClickHistory,
                )
            }

            Text(
                text = "Lv " + when (userRank.score) {
                    in 0..3999 -> UserRankName.OFF_CANDLE
                    in 4000..5999 -> UserRankName.CANDLE
                    in 6000..7999 -> UserRankName.FIRE
                    else -> UserRankName.BONFIRE
                }.ko,
                style = BetterAndroidTheme.typography.subtitle,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                color = BetterColors.Gray70,
            )

            Image(
                painterResource(
                    id = when (userRank.score) {
                        in 0..3999 -> R.drawable.ic_candle_empty
                        in 4000..5999 -> R.drawable.ic_candle
                        in 6000..7999 -> R.drawable.ic_fire_base
                        else -> R.drawable.ic_bonfire
                    },
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )

            val nextScore = when (userRank.score) {
                in 0..3999 -> UserRankName.CANDLE.limit
                in 4000..5999 -> UserRankName.FIRE.limit
                in 6000..7999 -> UserRankName.BONFIRE.limit
                else -> userRank.score
            }

            val leftScore = nextScore - userRank.score

            // TODO 그라디언트 구현
            LinearProgressIndicator(
                modifier = Modifier
                    .width(194.dp)
                    .height(20.dp)
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                color = BetterColors.Primary50,
                progress = if (leftScore - 2000 == 0) 0f else (leftScore.toFloat() / 2000),
                strokeCap = StrokeCap.Round,
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                text = "${userRank.score}점",
                style = BetterAndroidTheme.typography.body,
                color = BetterColors.Black,
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                text = "다음 단계 까지 ${leftScore}점 남았어요",
                style = BetterAndroidTheme.typography.option,
                color = BetterColors.TextGray,
            )
        }
    }
}
