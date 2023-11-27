package com.ssu.better.presentation.ui.main.home

import BetterCalendar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.task.UserTask
import com.ssu.better.entity.task.UserTaskStudy
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.component.StudyTaskCard
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.AnimatedTransitionDialog
import com.ssu.better.util.CustomDialogPosition
import com.ssu.better.util.calendarFormatStr
import com.ssu.better.util.customDialogModifier
import com.ssu.better.util.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val selectedDate by homeViewModel.selectedDate.collectAsStateWithLifecycle()

    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.loadStudyTaskList()
    }

    LaunchedEffect(selectedDate) {
        homeViewModel.loadStudyTaskList()
    }

    Scaffold {
        if (isDialogOpen) {
            AnimatedTransitionDialog(
                onDismissRequest = {
                    isDialogOpen = !isDialogOpen
                },
                ANIMATION_DURATION = 500L, // animation enter & exit duration
                DISMISS_DURATION = 1000L, // dialog dismiss duration
                modifier = Modifier
                    .customDialogModifier(CustomDialogPosition.TOP)
                    .fillMaxWidth(),
                content = { dialogHelper ->
                    BetterCalendar(
                        onDialogDismiss = {
                            dialogHelper::triggerAnimatedDismiss.invoke() // dialog 닫기
                        },
                        onClickDate = { date ->
                            homeViewModel.changeSelectedDate(date)
                            coroutineScope.launch {
                                delay(500L) // 닫기 delay
                                dialogHelper::triggerAnimatedDismiss.invoke()
                            }
                        },
                        selectedDate = selectedDate,
                        isDialogType = true, // 닫기 아이콘 활성화 여부를 위함
                    )
                },
            )
        }

        Column(
            modifier = Modifier
                .background(BetterColors.Bg)
                .padding(16.dp)
                .padding(bottom = 60.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = selectedDate.calendarFormatStr(),
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Gray90,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f)
                        .noRippleClickable { isDialogOpen = !isDialogOpen },
                    tint = BetterColors.Primary50,
                )
            }
            when (uiState) {
                is HomeViewModel.HomeUiState.Loading -> {
                    ShowLoadingAnimation()
                }

                is HomeViewModel.HomeUiState.Success -> {
                    StudyTaskListView(
                        list = (uiState as HomeViewModel.HomeUiState.Success).list,
                        onClickStudy = {},
                        onClickTaskChallenge = { studyId, title ->
                            navHostController.navigate(Screen.CreateTask.route + "?studyId=$studyId&title=$title")
                        },
                        baseDate = selectedDate,
                    )
                }

                is HomeViewModel.HomeUiState.Empty -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = stringResource(id = R.string.task_empty),
                    )
                }

                is HomeViewModel.HomeUiState.Fail -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = (uiState as HomeViewModel.HomeUiState.Fail).message,
                    )
                }
            }
        }
    }
}

@Composable
fun StudyTaskListView(
    list: Map<UserTaskStudy, List<UserTask>>,
    onClickStudy: (Long) -> Unit,
    onClickTaskChallenge: (Long, String) -> Unit,
    baseDate: LocalDate,
) {
    LazyColumn() {
        item {
            list.forEach {
                StudyTaskCard(
                    studyId = it.key.id,
                    studyTitle = it.key.title,
                    taskList = it.value,
                    baseDate = baseDate,
                    onClickMore = onClickStudy,
                    onClickChallenge = onClickTaskChallenge,
                )
            }
        }
    }
}
