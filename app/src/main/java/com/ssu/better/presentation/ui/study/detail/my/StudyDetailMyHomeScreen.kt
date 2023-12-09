package com.ssu.better.presentation.ui.study.detail.my

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.noRippleClickable
import okhttp3.internal.format

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudyDetailMyHomeScreen(
    navHostController: NavHostController,
    viewModel: StudyDetailMyHomeViewModel = hiltViewModel(),
    studyId: Long,
) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getStudyInfo(studyId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = when (uiState) {
                            is StudyDetailMyHomeViewModel.StudyDetailMyUiState.Loading -> {
                                ""
                            }

                            is StudyDetailMyHomeViewModel.StudyDetailMyUiState.Success -> {
                                (uiState as StudyDetailMyHomeViewModel.StudyDetailMyUiState.Success).study.title
                            }
                        },
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
            when (uiState) {
                is StudyDetailMyHomeViewModel.StudyDetailMyUiState.Loading -> {
                    ShowLoadingAnimation()
                }

                is StudyDetailMyHomeViewModel.StudyDetailMyUiState.Success -> {
                    val state = (uiState as StudyDetailMyHomeViewModel.StudyDetailMyUiState.Success)
                    Column(
                        Modifier
                            .padding(top = it.calculateTopPadding() + 10.dp)
                            .padding(horizontal = 12.dp),
                    ) {
                        StudyInfoCard(
                            state.nickname,
                            percent = state.percent,
                            challengeCount = state.challengeCount,
                            kickCount = state.kickCount,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 10.dp)
                                .padding(top = 25.dp).noRippleClickable {
                                    navHostController.navigate(
                                        Screen.StudyDetailMyTask
                                            .route + "?studyId=${state.study.studyId}",
                                    )
                                },
                        ) {
                            Text(text = stringResource(id = R.string.study_home_my_tasks), style = BetterAndroidTheme.typography.headline3)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 10.dp),
                        ) {
                            Text(text = stringResource(id = R.string.study_home_my_challenge), style = BetterAndroidTheme.typography.headline3)
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun StudyInfoCard(
    nickcname: String,
    percent: Int,
    challengeCount: Int,
    kickCount: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = BetterColors.gradation,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        Column(Modifier.padding(vertical = 20.dp, horizontal = 20.dp)) {
            Text(
                text = format(stringResource(id = R.string.task_my_progress), nickcname),
                color = BetterColors.White,
                style = BetterAndroidTheme.typography.headline3,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "$percent%",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.study_home_my_info_percent),
                            color = BetterColors.White,
                            style = BetterAndroidTheme.typography.headline3,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "$challengeCount",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.study_home_my_info_challenge),
                            color = BetterColors.White,
                            style = BetterAndroidTheme.typography.headline3,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "$kickCount",
                            textAlign = TextAlign.Center,
                            style = BetterAndroidTheme.typography.headline0,
                            color = BetterColors.White,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                        Text(
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
