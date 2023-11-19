package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.presentation.ui.study.join.StudyCheckDayCard
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyInfoScreen(study: Study) {
    Column {
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
    }
}
