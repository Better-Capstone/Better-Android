package com.ssu.better.presentation.ui.challenge.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.state.IdleEvent
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.getIcon
import com.ssu.better.util.toLocalDate
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ChallengeCreateScreen(
    navController: NavHostController,
    studyId: Long,
    taskId: Long,
) {
    val viewModel: ChallengeCreateViewModel = hiltViewModel()

    val event by viewModel.event.collectAsState()
    val idleEvent by viewModel.idleEvent.collectAsState()
    val description by viewModel.description.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()

    viewModel.load(studyId, taskId)

    ChallengeCreate(
        challengeCreateEvent = event,
        idleEvent = idleEvent,
        description = description,
        onDescriptionChanged = viewModel::updateDescription,
        imageUri = imageUri,
        onUriChanged = viewModel::updateImageUri,
        onClickVerifyButton = viewModel::postChallenge,
        finishAction = { navController.popBackStack() },
    )
}

@Composable
@Preview
fun PreviewChallengeCreate() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
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

    var description by remember { mutableStateOf("") }
    var imageUri: Uri? by remember { mutableStateOf(null) }
    ChallengeCreate(
        challengeCreateEvent = ChallengeCreateViewModel.ChallengeCreateEvent.Success(study = testStudy, task = testTask),
        idleEvent = IdleEvent.Idle,
        description = description,
        onDescriptionChanged = { value ->
            description = value
        },
        imageUri = imageUri,
        onUriChanged = { value ->
            imageUri = value
        },
        onClickVerifyButton = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeCreate(
    challengeCreateEvent: ChallengeCreateViewModel.ChallengeCreateEvent,
    idleEvent: IdleEvent,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    imageUri: Uri?,
    onUriChanged: (Uri?) -> Unit,
    onClickVerifyButton: () -> Unit,
    finishAction: () -> Unit = {},
) {
    LaunchedEffect(idleEvent) {
        if (idleEvent is IdleEvent.Finish) {
            finishAction()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = BetterColors.Bg)
                    .shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.create_challenge),
                        style = BetterAndroidTheme.typography.subtitle,
                        color = BetterColors.Black,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = finishAction) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        when (challengeCreateEvent) {
            is ChallengeCreateViewModel.ChallengeCreateEvent.Load -> {
                ShowLoadingAnimation()
            }

            is ChallengeCreateViewModel.ChallengeCreateEvent.Success -> {
                val pattern = "yyyy-MM-dd"
                val launhcer = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    onUriChanged(uri)
                }
                Column(modifier = Modifier.padding(paddingValues)) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp, top = 30.dp),
                        text = LocalDate.now().format(DateTimeFormatter.ofPattern(pattern)) ?: "",
                        style = BetterAndroidTheme.typography.headline4,
                        color = BetterColors.Gray30,
                    )

                    Row(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(18.4.dp),
                            painter = painterResource(id = challengeCreateEvent.study.category.getIcon()),
                            contentDescription = null,
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = challengeCreateEvent.study.title,
                            style = BetterAndroidTheme.typography.headline2,
                            color = BetterColors.Black,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BetterRoundChip(enabled = true, text = "${challengeCreateEvent.study.taskGroupList.size}회차", onClick = {})
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = challengeCreateEvent.task.title,
                            style = BetterAndroidTheme.typography.subtitle,
                            color = BetterColors.Black,
                        )
                    }

                    BetterTextField(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .height(110.dp)
                            .shadow(elevation = 10.dp),
                        value = description,
                        onValueChange = onDescriptionChanged,
                        hint = stringResource(id = R.string.description),
                        textStyle = BetterAndroidTheme.typography.body.copy(textAlign = TextAlign.Start),
                        isSingleLine = false,
                        hintAlignment = Alignment.TopStart,
                        backgroundColor = BetterColors.White,
                    )

                    if (imageUri == null) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(284.dp)
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                            onClick = {
                                launhcer.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                                    ),
                                )
                            },
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
                        val painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = imageUri)
                                .build(),
                        )

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(284.dp)
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    BetterButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        text = stringResource(id = R.string.verify),
                        type = BetterButtonType.DEFAULT,
                        onClick = onClickVerifyButton,
                    )
                }
            }
        }
    }
}
