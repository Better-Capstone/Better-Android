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
import com.ssu.better.entity.user.ScoreUser
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun MemberTaskItem(
    modifier: Modifier = Modifier,
    taskId: Long,
    taskTitle: String,
    userScore: ScoreUser,
    isCompleted: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleRankProfile(score = userScore.score, modifier = Modifier.size(40.dp))
        Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(
                text = userScore.nickname,
                style = BetterAndroidTheme.typography.caption,
                color = BetterColors.Gray70,
            )
            Text(
                text = taskTitle,
                style = BetterAndroidTheme.typography.caption,
                color = BetterColors.Gray30,
            )
        }

        BetterRoundChip(
            enabled = !isCompleted,
            text = if (isCompleted) {
                stringResource(id = R.string.task_end)
            } else {
                stringResource(id = R.string.task_inprogress)
            },
            onClick = onClick,
        )
    }
}
