package com.ssu.better.presentation.ui.challenge.approve

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ssu.better.R
import com.ssu.better.entity.challenge.Challenge
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
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.presentation.component.CircleRankProfile
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.state.IdleEvent
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toLocalDate
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ChallengeApproveScreen(
    navController: NavHostController,
    challengeId: Long,
    studyId: Long,
    userName: String,
    userScore: Int,
) {
    val viewModel: ChallengeApproveViewModel = hiltViewModel()

    val event by viewModel.event.collectAsState()
    val idleEvent by viewModel.idleEvent.collectAsState()

    viewModel.load(studyId, challengeId)
    ChallengeApproveContent(
        event = event,
        idleEvent = idleEvent,
        userName = userName,
        userScore = userScore,
        onClickFinish = { navController.popBackStack() },
        onClickApprove = viewModel::onClickApprove,
        onClickReject = viewModel::onClickReject,
    )
}

@Preview
@Composable
fun PreviewApproveScreen() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testMember = Member(1, 1, MemberType.MEMBER, time)
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
    val testGroupRank = GroupRank(1, 18000)
    val tasks = List(2) { testTask }.toMutableList()
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
        arrayListOf(testTaskGroup),
        arrayListOf(testUserRankHistory),
        testGroupRank,
        "",
    )

    val challenge = Challenge(
        id = 1,
        description = "1일 1커밋",
        image = "https://velog.velcdn.com/images/spy03128/post/f9592660-5745-4317-bcc8-96cff69b368e/image.png",
        approveMember = arrayListOf(1, 2, 3),
        rejectMember = arrayListOf(4, 5, 6),
        createdAt = "2023-11-26T12:51:34.239234",
        updatedAt = "2023-11-26T12:51:34.239234",
    )
    ChallengeApproveContent(
        event = ChallengeApproveViewModel.ChallengeApproveEvent.Success(challenge, testStudy, true),
        idleEvent = IdleEvent.Idle,
        userName = "개발하는 북극곰",
        userScore = 5000,
        onClickFinish = { },
        onClickApprove = { },
        onClickReject = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeApproveContent(
    event: ChallengeApproveViewModel.ChallengeApproveEvent,
    idleEvent: IdleEvent,
    userName: String,
    userScore: Int,
    onClickFinish: () -> Unit,
    onClickApprove: () -> Unit,
    onClickReject: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = BetterColors.Bg)
                    .shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.verify_challenge),
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
        LaunchedEffect(idleEvent) {
            if (idleEvent is IdleEvent.Finish) {
                onClickFinish()
            }
        }
        when (event) {
            is ChallengeApproveViewModel.ChallengeApproveEvent.Load -> {
                ShowLoadingAnimation()
            }

            is ChallengeApproveViewModel.ChallengeApproveEvent.Success -> {
                val pattern = "yyyy-MM-dd"
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(BetterColors.Bg)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp, top = 30.dp),
                        text = event.challenge.createdAt.toLocalDate("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                            ?.format(DateTimeFormatter.ofPattern(pattern)) ?: "",
                        style = BetterAndroidTheme.typography.headline4,
                        color = BetterColors.Gray30,
                    )

                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CircleRankProfile(score = userScore.toLong(), modifier = Modifier.size(40.dp))

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = userName,
                            style = BetterAndroidTheme.typography.headline3,
                            color = BetterColors.Black,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BetterRoundChip(enabled = true, text = "${event.study.taskGroupList.size}회차", onClick = {})
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "제목",
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Black,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .height(110.dp)
                            .background(color = BetterColors.White)
                            .clip(RoundedCornerShape(10.dp)),
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = event.challenge.description,
                            style = BetterAndroidTheme.typography.body.copy(textAlign = TextAlign.Start),
                        )
                    }

                    if (event.challenge.image.isEmpty()) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(284.dp)
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                            onClick = { },
                            enabled = false,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BetterColors.Gray00,
                                contentColor = BetterColors.Primary50,
                            ),
                        ) {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = null,
                            )
                        }
                    } else {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(284.dp)
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = rememberAsyncImagePainter(event.challenge.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    var count = ((event.study.memberCount / 2) - event.challenge.approveMember.size)
                    if (count < 0) {
                        count = 0
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 20.dp)
                            .fillMaxWidth(),
                        text = "인증 확인까지 " + count + "회 남았습니다.",
                        style = BetterAndroidTheme.typography.headline3,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = onClickApprove,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BetterColors.Black,
                                contentColor = BetterColors.White,
                                disabledContainerColor = BetterColors.Gray20,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            enabled = !event.isChecked,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_thumb_up),
                                contentDescription = null,
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = onClickReject,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BetterColors.Primary50,
                                contentColor = BetterColors.White,
                                disabledContainerColor = BetterColors.Primary10,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            enabled = !event.isChecked,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_thumb_down),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}
