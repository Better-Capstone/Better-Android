package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.task.StudyTask
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.ScoreUser
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toLocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun UserChallengeCard(
    studyTask: StudyTask,
    memberCount: Int,
    onClick: (StudyTask) -> Unit = { },
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(141.dp)
            .padding(10.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (studyTask.challenge != null) {
                    onClick(studyTask)
                }
            },
    ) {
        val isCompleted = studyTask.challenge != null && (studyTask.challenge!!.approveMember.size >= memberCount / 2)
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(18.dp))
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(84.dp),
                painter =
                if (isCompleted) {
                    painterResource(id = R.drawable.ic_challenge_approved)
                } else {
                    painterResource(id = R.drawable.ic_challenge)
                },
                contentDescription = null,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = studyTask.user.nickname,
                style = BetterAndroidTheme.typography.headline4,
                color = BetterColors.Black,
            )
        }
    }
}

@Preview
@Composable
fun PreviewUserChallengeCard() {
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
    val testChallenge = Challenge(
        id = 1,
        description = "설명설명",
        image = "",
        approveMember = arrayListOf(1, 2, 3, 4),
        rejectMember = arrayListOf(5, 6),
        createdAt = "",
        updatedAt = "",
    )
    val testTask = StudyTask(
        taskId = 1,
        taskGroup = testTaskGroup,
        member = testMember,
        challenge = testChallenge,
        createdAt = "",
        updatedAt = "",
        title = "",
        user = ScoreUser(1, "개발하는 북극곰", 5000),
        study = SimpleStudy(1, "컴포즈 스터디"),
        userRankHistory = arrayListOf(),
    )

    UserChallengeCard(studyTask = testTask, 2)
}
