package com.ssu.better.presentation.ui.challenge.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.getIcon
import com.ssu.better.util.toLocalDate
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ChallengeCreateScreen(navController: NavHostController) {
}

@Composable
@Preview
fun PreviewChallengeCreate() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testMember = Member(1, 1, MemberType.MEMBER, time)
    val testTask = Task(1, 1, time, 1, 1, time, time, "제목")
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val tasks = List(2) { testTask }.toMutableList()
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
        ArrayList(tasks),
        arrayListOf(testUserRankHistory),
        testGroupRank,
    )
    ChallengeCreateContent(
        task = testTask,
        study = testStudy,
        onClickFinish = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeCreateContent(
    task: Task,
    study: Study,
    onClickFinish: () -> Unit,
) {
    val pattern = "yyyy-MM-dd"
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
                    painter = painterResource(id = study.category.getIcon()),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = study.category.name,
                    style = BetterAndroidTheme.typography.headline2,
                    color = BetterColors.Black,
                )
            }

            Row(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BetterRoundChip(enabled = true, text = "89회차", onClick = {})
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = task.title,
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Black,
                )
            }
        }
    }
}
