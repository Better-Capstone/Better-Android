package com.ssu.better.presentation.ui.task.crate

import BetterCalendar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateTaskScreen(
    navHostController: NavHostController,
    createViewModel: TaskCreateViewModel = hiltViewModel(),
    // study: Study,
) {
    val selectedDate by createViewModel.selectedDate.collectAsStateWithLifecycle()
    val content by createViewModel.content.collectAsStateWithLifecycle()

    val CONTENT_MAX = 100

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.background(
                    BetterColors.Bg,
                ).padding(vertical = 10.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painterResource(
                        id = R.drawable.ic_back,
                    ),
                    modifier = Modifier.size(24.dp).clickable { },
                    contentDescription = null,
                )

                Text(
                    text = "스터디 이름",
                    modifier = Modifier.weight(1f).offset(x = -12.dp),
                    textAlign = Center,
                    style = BetterAndroidTheme.typography.headline3,
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 40.dp),
            ) {
                BetterCalendar(onClickDate = createViewModel::onChangeSelectedDate, selectedDate = selectedDate)
                Column(Modifier.padding(horizontal = 20.dp)) {
                    BetterTextField(
                        value = content,
                        onValueChange = createViewModel::onChangeContent,
                        hint = stringResource(id = R.string.task_create_hint),
                        counterMaxLength = CONTENT_MAX,
                    )
                    Text(
                        text = "${content.length}/$CONTENT_MAX",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = End,
                        style = BetterAndroidTheme.typography.option,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    BetterButton(
                        text = stringResource(id = R.string.button_complete),
                        type = BetterButtonType.DEFAULT,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = content.isNotBlank() && content.length <= CONTENT_MAX,
                        onClick = {},
                    )
                }
            }
        },
    )
}
