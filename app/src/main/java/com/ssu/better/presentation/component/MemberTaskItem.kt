package com.ssu.better.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.UserRank
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.convertToLocalDateByFormat
import java.time.LocalDate
import java.time.Period

@Composable
fun MemberTaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    userRank: UserRank,
    onClick: () -> Unit,
) {
    val taskEnabled = Period.between(LocalDate.now(), convertToLocalDateByFormat(task.taskGroup.endDate, "yyyy-MM-dd")).days >= 0

    Row(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleRankProfile(score = userRank.score.toLong(), modifier = Modifier.size(40.dp))
        Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(
                text = userRank.user.nickname,
                style = BetterAndroidTheme.typography.caption,
                color = BetterColors.Gray70,
            )
            Text(
                text = task.title,
                style = BetterAndroidTheme.typography.caption,
                color = BetterColors.Gray30,
            )
        }

        BetterRoundChip(
            enabled = taskEnabled,
            text = if (taskEnabled) {
                stringResource(id = R.string.task_inprogress)
            } else {
                stringResource(id = R.string.task_end)
            },
            onClick = onClick,
        )
    }
}
