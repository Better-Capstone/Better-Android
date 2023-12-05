package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.StudyTask
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.presentation.component.GradientProgressIndicator
import com.ssu.better.presentation.component.StudyHomeTaskCard
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import java.time.LocalDate
import java.time.ZoneOffset

@Composable
fun StudyHomeScreen(
    study: Study,
    taskList: ArrayList<StudyTask>,
    onClickMember: () -> Unit = { },
    onClickReport: () -> Unit = { },
    onClickMyStudy: () -> Unit = { },
    onClickAdd: (Study) -> Unit = { },
    onClickTask: (StudyTask) -> Unit = { },
) {
    Column {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
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
                                text = study.title,
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
                                progress = if (taskList.size > 0) taskList.size.toFloat() / study.memberCount else 0f,
                                strokeWidth = 16.dp,
                            )

                            BetterRoundChip(
                                modifier = Modifier.padding(top = 18.dp, bottom = 25.dp),
                                enabled = true,
                                text = "${study.taskGroupList.size}회차",
                                onClick = {},
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(122.dp)
                        .background(
                            brush = BetterColors.gradation,
                            shape = RoundedCornerShape(10.dp),
                        ),
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(start = 30.dp, end = 30.dp)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(79.dp)
                                    .height(100.dp)
                                    .padding(top = 20.dp)
                                    .clickable {
                                        onClickMember()
                                    },
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .width(51.dp)
                                            .height(46.dp),
                                        text = study.memberCount.toString(),
                                        textAlign = TextAlign.Center,
                                        style = BetterAndroidTheme.typography.headline0,
                                        color = BetterColors.White,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .width(79.dp)
                                            .height(24.dp),
                                        text = stringResource(id = R.string.member),
                                        color = BetterColors.White,
                                        style = BetterAndroidTheme.typography.headline3,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .width(79.dp)
                                    .height(100.dp)
                                    .padding(top = 20.dp)
                                    .clickable {
                                        onClickReport()
                                    },
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Image(
                                        modifier = Modifier.size(48.dp),
                                        painter = painterResource(id = R.drawable.ic_report),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(BetterColors.White),
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .width(79.dp)
                                            .height(24.dp),
                                        text = stringResource(id = R.string.periodic_reports),
                                        color = BetterColors.White,
                                        style = BetterAndroidTheme.typography.headline3,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .width(79.dp)
                                    .height(100.dp)
                                    .padding(top = 20.dp)
                                    .clickable {
                                        onClickMyStudy()
                                    },
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Image(
                                        modifier = Modifier.size(48.dp),
                                        painter = painterResource(id = R.drawable.ic_person),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(BetterColors.White),
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .width(79.dp)
                                            .height(24.dp),
                                        text = stringResource(id = R.string.my_study),
                                        color = BetterColors.White,
                                        style = BetterAndroidTheme.typography.headline3,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    }
                }

                val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
                StudyHomeTaskCard(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                    study = study,
                    baseDate = testTime,
                    onClickAdd = onClickAdd,
                    onClickTask = onClickTask,
                    taskList = taskList,
                )
            }
        }
    }
}
