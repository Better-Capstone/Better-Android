package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.Study
import com.ssu.better.presentation.component.GradientProgressIndicator
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyHomeScreen(study: Study) {
    Column {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(307.78.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                        ) {
                            Text(
                                text = "${study.title} 스터디",
                                style = BetterAndroidTheme.typography.headline3,
                                color = BetterColors.Primary50,
                            )

                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = stringResource(id = R.string.current_progress),
                                style = BetterAndroidTheme.typography.headline3,
                                color = BetterColors.Gray30,
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            GradientProgressIndicator(
                                modifier = Modifier.size(156.dp),
                                progress = 0.78f,
                                strokeWidth = 16.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}
