package com.ssu.better.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Status
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskGroup
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.convertToLocalDateByFormat
import com.ssu.better.util.getDDay
import com.ssu.better.util.toLocalDate
import java.time.LocalDate
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    baseDate: LocalDate = LocalDate.now(),
    task: Task,
    onClick: () -> Unit,

) {
    val taskEnabled = Period.between(LocalDate.now(), convertToLocalDateByFormat(task.taskGroup.endDate, "yyyy-MM-dd")).days >= 0

    Row(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = getDDay(baseDate, convertToLocalDateByFormat(task.taskGroup.endDate, "yyyy-MM-dd") ?: baseDate),
            style = BetterAndroidTheme.typography.headline3,
            color = BetterColors.Primary50,
        )
        Text(
            text = task.title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            style = BetterAndroidTheme.typography.headline3,
            color = BetterColors.Gray90,
        )

        BetterRoundChip(enabled = taskEnabled, text = "인증하기", onClick = onClick)
    }
}

@Preview
@Composable
fun PreviewTask() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val time = "2023-11-18T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""

    val deadline = "2023-11-11T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testMember = Member(1, 1, MemberType.MEMBER, "")
    val testTaskGroup = TaskGroup(
        taskGroupId = 1,
        status = Status.INPROGRESS,
        startDate = "",
        endDate = deadline,
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
        title = "운영체제",
    )

    println(time)
    TaskItem(
        task = testTask,
        onClick = {},
    )
}
