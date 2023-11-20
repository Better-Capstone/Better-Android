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
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyStatus
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.entity.user.UserRankName
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
            navHostController.navigate(Screen.StudyJoin.route + "?studyId=${study.studyId}")
        },
    )
}

@Composable
fun MyPageContent(
    uiState: MyPageViewModel.UIState,
    isNotifyEnabled: Boolean,
    onClickNotifyChange: (Boolean) -> Unit,
    onClickStudy: (Study) -> Unit,
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
) {
    Surface(color = BetterColors.Gray00) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            UserRankCard(userName = "배현빈", userRank = userRank)

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
                            .width(141.dp)
                            .height(165.dp),
                        study = item,
                        onClick = {
                            onClickStudy(item)
                        },
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
            ) {
                Text(
                    text = "알림 설정",
                    style = BetterAndroidTheme.typography.headline3,
                    color = BetterColors.Black,
                )

                Spacer(Modifier.weight(1f))

                Text(
                    modifier = Modifier.clickable { onClickNotifyEnabledChange(true) },
                    text = "ON",
                    color = if (isNotifyEnabled) BetterColors.Primary50 else BetterColors.Gray20,
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { onClickNotifyEnabledChange(false) },
                    text = "OFF",
                    color = if (!isNotifyEnabled) BetterColors.Primary50 else BetterColors.Gray20,
                )
            }

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
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testUserRank = UserRank(
        id = 3,
        user = testUser,
        score = 7530,
        createdAt = "",
        updatedAt = "",
        userRankHistoryList = arrayListOf(testUserRankHistory),
    )

    val testMember = Member(1, 1, MemberType.MEMBER, "")
    val testTask = Task(1, 1, "", 1, 1, "", "", "제목")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "제목",
        "설명",
        StudyStatus.INPROGRESS,
        StudyPeriod.EVERYDAY,
        StudyCheckDay.EVERYDAY,
        5,
        1,
        10,
        1500,
        arrayListOf(testMember),
        arrayListOf(testTask),
        arrayListOf(testUserRankHistory),
        testGroupRank,
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
    )
}

@Composable
fun UserRankCard(userName: String, userRank: UserRank) {
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
