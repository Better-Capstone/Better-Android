package com.ssu.better.presentation.ui.task.crate

import BetterCalendar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.component.SuccessScreen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import kotlinx.coroutines.delay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateTaskScreen(
    navHostController: NavHostController,
    createViewModel: CreateTaskViewModel = hiltViewModel(),
    studyId: Long,
) {
    val CONTENT_MAX = 100

    val uiState by createViewModel.uiState.collectAsStateWithLifecycle()
    val event by createViewModel.event.collectAsStateWithLifecycle(CreateTaskViewModel.CreateTaskEvent.Loading)

    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(isDialogOpen) {
        if (isDialogOpen) {
            delay(1000)
            navHostController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        createViewModel.getStudy(studyId)
    }

    Scaffold(
        topBar = {
            if (!isDialogOpen) {
                Row(
                    modifier = Modifier
                        .background(
                            BetterColors.Bg,
                        )
                        .padding(vertical = 10.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = BetterColors.Bg,
                            contentColor = BetterColors.Gray90,
                        ),
                        onClick = {
                            navHostController.popBackStack()
                        },
                    ) {
                        Icon(
                            painterResource(
                                id = R.drawable.ic_back,
                            ),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                        )
                    }

                    Text(
                        text = uiState.study?.title ?: "",
                        modifier = Modifier
                            .weight(1f)
                            .offset(x = -12.dp),
                        textAlign = Center,
                        style = BetterAndroidTheme.typography.headline3,
                    )
                }
            }
        },
        content = {
            if (isDialogOpen) {
                AlertDialog(
                    onDismissRequest = {},
                ) {
                    SuccessScreen(modifier = Modifier.fillMaxSize(), message = "테스크 생성 성공", contentColor = BetterColors.White)
                }
            } else {
                when (event) {
                    is CreateTaskViewModel.CreateTaskEvent.Loading -> {
                        ShowLoadingAnimation()
                    }

                    is CreateTaskViewModel.CreateTaskEvent.Success -> {
                        val study = uiState.study ?: return@Scaffold
                        val checkDay: LocalDate = createViewModel.getNextCheckDay(study)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 40.dp),
                        ) {
                            BetterCalendar(onClickDate = {}, selectedDate = checkDay)
                            Column(Modifier.padding(horizontal = 20.dp)) {
                                BetterTextField(
                                    value = uiState.content,
                                    onValueChange = createViewModel::onChangeContent,
                                    hint = stringResource(id = R.string.task_create_hint),
                                    counterMaxLength = CONTENT_MAX,
                                )
                                Text(
                                    text = "${uiState.content.length}/$CONTENT_MAX",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = End,
                                    style = BetterAndroidTheme.typography.option,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                BetterButton(
                                    text = stringResource(id = R.string.button_complete),
                                    type = BetterButtonType.DEFAULT,
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = uiState.content.isNotBlank() && uiState.content.length <= CONTENT_MAX,
                                    onClick = { createViewModel.onClickComplete(uiState.study?.studyId ?: return@BetterButton) },
                                )
                            }
                        }
                    }

                    is CreateTaskViewModel.CreateTaskEvent.Fail -> {
                        ErrorScreen(modifier = Modifier.fillMaxSize(), message = (event as CreateTaskViewModel.CreateTaskEvent.Fail).message)
                    }

                    is CreateTaskViewModel.CreateTaskEvent.Complete -> {
                        isDialogOpen = true
                    }
                }
            }
        },
    )
}
