package com.ssu.better.presentation.ui.study.detail.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.toCalendar
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyMyTasksScreen(
    navHostController: NavHostController,
    viewModel: StudyMyTasksViewModel = hiltViewModel(),
    studyId: Long,
) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getStudyTasks(studyId)
    }

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.study_home_my_tasks),
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
    ) {
        Column(Modifier.padding(top = it.calculateTopPadding() + 4.dp)) {
            when (uiState) {
                is StudyMyTasksViewModel.MyTasksUiState.Loading -> {
                    ShowLoadingAnimation()
                }

                is StudyMyTasksViewModel.MyTasksUiState.Empty -> {
                    ErrorScreen(modifier = Modifier.fillMaxSize(), message = "아직 등록된 테스크가 없습니다.")
                }

                is StudyMyTasksViewModel.MyTasksUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().background(BetterColors.Bg).padding(10.dp),
                    ) {
                        item {
                            (uiState as StudyMyTasksViewModel.MyTasksUiState.Success).tasks.forEach {
                                StudyHistoryItem(createdAt = it.createdAt, title = it.title)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudyHistoryItem(
    createdAt: String,
    title: String,
) {
    val calendar = createdAt.toCalendar()
    Box(Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 2.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(BetterColors.White),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 20.dp),
            ) {
                Text(
                    text = "${calendar?.get(Calendar.YEAR)}.${calendar?.get(Calendar.MONTH)}.${calendar?.get(Calendar.DAY_OF_MONTH)}",
                    style = BetterAndroidTheme.typography.caption,
                    color = BetterColors.Gray30,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                )
                Text(
                    text = title,
                    style = BetterAndroidTheme.typography.headline3,
                    color = BetterColors.Gray90,
                )
            }
        }
    }
}
