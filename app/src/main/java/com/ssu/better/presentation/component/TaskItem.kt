package com.ssu.better.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.task.Task
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
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
    val taskEnabled = Period.between(task.deadline.toLocalDate(), baseDate).isNegative

    Row(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = getDDay(baseDate, task.deadline.toLocalDate() ?: baseDate),
            style = BetterAndroidTheme.typography.headline3,
            color = BetterColors.Primary50,
        )
        Text(
            text = task.title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            style = BetterAndroidTheme.typography.headline3,
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

    println(time)
    TaskItem(
        task = Task(
            id = 111L,
            111L,
            deadline = deadline,
            111L,
            111L,
            title = "운영체제",
            createdAt = time,
            updatedAt = time,
        ),
        onClick = {},
    )
}
