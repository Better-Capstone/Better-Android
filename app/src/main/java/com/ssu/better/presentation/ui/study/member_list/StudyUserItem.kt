package com.ssu.better.presentation.ui.study.member_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyUserItem(user: StudyUser) {
    StudyUserContent(nickname = user.nickname)
}

@Composable
fun StudyUserContent(nickname: String) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .width(68.dp)
            .height(75.dp)
            .background(color = BetterColors.White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_better_logo),
                contentDescription = "better",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(48.dp)
                    .background(color = BetterColors.Gray00, shape = CircleShape)
                    .clip(CircleShape)
                    .padding(5.dp),
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                text = nickname,
                style = BetterAndroidTheme.typography.body,
                color = BetterColors.Black,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun PreviewStudyUser() {
    StudyUserContent(nickname = "사용자명")
}
