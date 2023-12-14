package com.ssu.better.presentation.ui.study.join

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyUserBox(studyUser: StudyUser) {
    Box(
        modifier = Modifier
            .width(75.dp)
            .height(87.dp)
            .background(color = BetterColors.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(43.dp)
                    .background(color = BetterColors.Gray00, shape = CircleShape),
                painter = painterResource(
                    id = when (studyUser.userRank.score) {
                        in 0..3999 -> R.drawable.ic_candle_empty
                        in 4000..5999 -> R.drawable.ic_candle
                        in 6000..7999 -> R.drawable.ic_fire_base
                        else -> R.drawable.ic_bonfire
                    },
                ),
                contentDescription = "사용자 등급",
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                maxLines = 1,
                text = studyUser.nickname,
                style = BetterAndroidTheme.typography.body,
                color = BetterColors.Black,
            )
        }
    }
}
