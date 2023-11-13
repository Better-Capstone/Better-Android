package com.ssu.better.presentation.ui.study_join

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Preview
@Composable
fun StudyUser() {
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
                painter = painterResource(id = R.drawable.ic_fire_base),
                contentDescription = "사용자 등급",
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = "사용자명",
                style = BetterAndroidTheme.typography.body,
                color = BetterColors.Black,
            )
        }
    }
}
