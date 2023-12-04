package com.ssu.better.presentation.ui.main.user_rank_history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toCalendar
import java.util.Calendar

@Composable
fun UserRankHistoryCard(userRankHistory: UserRankHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(63.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        val calendar = userRankHistory.createdAt.toCalendar()
        Row(modifier = Modifier.fillMaxHeight()) {
            Column {
                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                    Text(
                        text = "${calendar?.get(Calendar.MONTH)}.${calendar?.get(Calendar.DAY_OF_MONTH)}",
                        color = BetterColors.Primary20,
                        style = BetterAndroidTheme.typography.option,
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    text = userRankHistory.description,
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Black,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    textAlign = TextAlign.Center,
                    text = if (userRankHistory.score > 0) "+${userRankHistory.score}" else userRankHistory.score.toString(),
                    style = BetterAndroidTheme.typography.headline2,
                    color = if (userRankHistory.score > 0) BetterColors.Primary50 else BetterColors.Black,
                )
            }
        }
    }
}

@Preview
@Composable
fun PerviewUserRankHistoryCard() {
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
        50,
        "50점 추가",
        testTask,
        "2023-12-04T00:00:02.815615",
        "2023-12-04T00:00:02.815615",
    )
    UserRankHistoryCard(userRankHistory = testUserRankHistory)
}
