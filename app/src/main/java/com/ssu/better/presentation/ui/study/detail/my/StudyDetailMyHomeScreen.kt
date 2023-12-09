package com.ssu.better.presentation.ui.study.detail.my

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import okhttp3.internal.format

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudyDetailMyHomeScreen(
    navHostController: NavHostController,
    studyId: Long,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = "",
                        style = BetterAndroidTheme.typography.headline3,
                        color = BetterColors.Black,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        content = {
            Column(Modifier.padding(top = it.calculateTopPadding() + 10.dp).padding(horizontal = 12.dp)) {
                StudyInfoCard()
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 10.dp).padding(top = 25.dp)) {
                    Text(text = stringResource(id = R.string.study_home_my_tasks), style = BetterAndroidTheme.typography.headline3)
                }
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 10.dp)) {
                    Text(text = stringResource(id = R.string.study_home_my_challenge), style = BetterAndroidTheme.typography.headline3)
                }
            }
        },
    )
}

@Composable
fun StudyInfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = BetterColors.gradation,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        Column(Modifier.padding(vertical = 12.dp, horizontal = 20.dp)) {
            Text(
                text = format(stringResource(id = R.string.task_my_progress), ""),
                color = BetterColors.White,
                style = BetterAndroidTheme.typography.headline3,
                modifier = Modifier.padding(top = 8.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                        .padding(top = 20.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .width(51.dp)
                                .height(46.dp),
                            text = "78",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .width(79.dp)
                                .height(24.dp),
                            text = stringResource(id = R.string.study_home_my_info_percent),
                            color = BetterColors.White,
                            style = BetterAndroidTheme.typography.headline3,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f)
                        .padding(top = 20.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .width(51.dp)
                                .height(46.dp),
                            text = "82",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .width(79.dp)
                                .height(24.dp),
                            text = stringResource(id = R.string.study_home_my_info_percent),
                            color = BetterColors.White,
                            style = BetterAndroidTheme.typography.headline3,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f)
                        .padding(top = 20.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .width(51.dp)
                                .height(46.dp),
                            text = "1",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .width(79.dp)
                                .height(24.dp),
                            text = stringResource(id = R.string.study_home_my_info_count),
                            color = BetterColors.White,
                            style = BetterAndroidTheme.typography.headline3,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
