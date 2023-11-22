package com.ssu.better.presentation.ui.study.join

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyJoinScreen(
    navController: NavController,
    studyId: Int,
    viewModel: StudyJoinViewModel = hiltViewModel(),
) {
    viewModel.setStudyId(studyId)
    val uiState = viewModel.uiState.collectAsState()
    val hour = viewModel.hour.collectAsState()
    val minute = viewModel.minute.collectAsState()
    val isAm = viewModel.isAm.collectAsState()
    StudyJoinContent(
        uiState = uiState.value,
        hour = hour.value,
        minute = minute.value,
        isAm = isAm.value,
        onHourChanged = viewModel::updateHour,
        onMinuteChanged = viewModel::updateMinute,
        onClickTime = viewModel::updateIsAm,
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
    val testTask = Task(1, 1, "", 1, 1, "", "", "제목")
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "알고리즘 스터디",
        "스터디 설명",
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
    StudyJoin(study = testStudy, onClickFinish = {}, "12", "30", { }, { }, { }, { }, true)
}

@Composable
fun StudyJoinContent(
    uiState: StudyJoinViewModel.UIState,
    hour: String,
    minute: String,
    isAm: Boolean,
    onHourChanged: (String) -> Unit,
    onMinuteChanged: (String) -> Unit,
    onClickTime: (Boolean) -> Unit,
    onClickJoinButton: () -> Unit,
    onClickFinish: () -> Unit,
) {
    when (uiState) {
        is StudyJoinViewModel.UIState.Success -> {
            StudyJoin(
                study = uiState.study,
                onClickFinish = onClickFinish,
                hour = hour,
                minute = minute,
                onHourChanged = onHourChanged,
                onMinuteChanged = onMinuteChanged,
                onClickTime = onClickTime,
                onClickJoinButton = onClickJoinButton,
                isAm = isAm,
            )
        }
        is StudyJoinViewModel.UIState.Loading -> {
            ShowLoadingAnimation()
        }

        else -> {
            onClickFinish()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyJoin(
    study: Study,
    onClickFinish: () -> Unit,
    hour: String,
    minute: String,
    onHourChanged: (String) -> Unit,
    onMinuteChanged: (String) -> Unit,
    onClickTime: (Boolean) -> Unit,
    onClickJoinButton: () -> Unit,
    isAm: Boolean,
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

                    StudyCheckDayCard(study.checkDay)

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

            items(study.memberList.size) { index ->
                StudyUser()
            }

            item(span = { GridItemSpan(4) }) {
                Column(modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)) {
                    Text(
                        text = "알람 설정",
                        style = BetterAndroidTheme.typography.subtitle,
                        color = BetterColors.Black,
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BetterTextField(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp),
                            value = hour,
                            textStyle = BetterAndroidTheme.typography.body.copy(textAlign = TextAlign.Center),
                            onValueChange = onHourChanged,
                        )

                        Text(
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                            text = ":",
                            style = BetterAndroidTheme.typography.title,
                            color = BetterColors.Black,
                        )

                        BetterTextField(
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp),
                            value = minute,
                            textStyle = BetterAndroidTheme.typography.body.copy(textAlign = TextAlign.Center),
                            onValueChange = onMinuteChanged,
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .clickable { onClickTime(true) },
                            text = "AM",
                            style = BetterAndroidTheme.typography.headline3,
                            color = if (isAm) BetterColors.Primary50 else BetterColors.Gray10,
                        )

                        Text(
                            text = "|",
                            style = BetterAndroidTheme.typography.headline3,
                            color = BetterColors.Gray20,
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .clickable { onClickTime(false) },
                            text = "PM",
                            style = BetterAndroidTheme.typography.headline3,
                            color = if (!isAm) BetterColors.Primary50 else BetterColors.Gray10,
                        )
                    }

                    Spacer(modifier = Modifier.height(74.dp))

                    BetterButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "참여하기",
                        type = BetterButtonType.DEFAULT,
                        onClick = onClickJoinButton,
                    )
                }
            }
        }
    }
}
