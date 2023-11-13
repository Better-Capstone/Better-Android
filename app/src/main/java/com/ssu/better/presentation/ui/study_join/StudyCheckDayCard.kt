package com.ssu.better.presentation.ui.study_join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.presentation.component.BetterTextBox
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyCheckDayCard(
    checkDay: StudyCheckDay,
    onClick: (StudyCheckDay) -> Unit = { },
    topPadding: Dp = 30.dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = topPadding, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BetterTextBox(
            text = "월",
            backgroundColor = if ((checkDay == StudyCheckDay.MON || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },

        )

        BetterTextBox(
            text = "화",
            backgroundColor = if ((checkDay == StudyCheckDay.TUE || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )

        BetterTextBox(
            text = "수",
            backgroundColor = if ((checkDay == StudyCheckDay.WED || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )

        BetterTextBox(
            text = "목",
            backgroundColor = if ((checkDay == StudyCheckDay.THU || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )

        BetterTextBox(
            text = "금",
            backgroundColor = if ((checkDay == StudyCheckDay.FRI || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )

        BetterTextBox(
            text = "토",
            backgroundColor = if ((checkDay == StudyCheckDay.SAT || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )

        BetterTextBox(
            text = "일",
            backgroundColor = if ((checkDay == StudyCheckDay.SUN || checkDay == StudyCheckDay.EVERYDAY)) {
                BetterColors.Primary00
            } else {
                BetterColors.Gray00
            },
            textColor = BetterColors.Gray90,
            width = 35.dp,
            height = 35.dp,
            onClick = {
                onClick(checkDay)
            },
        )
    }
}
