package com.ssu.better.presentation.ui.onboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun OnBoardScreen(
    navController: NavHostController,
    viewModel: OnBoardViewModel = hiltViewModel(),
) {
    val uistate by viewModel.uiState.collectAsState()

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().imePadding()
                .fillMaxWidth().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "${uistate.name}님, 환영합니다!",
                style = BetterAndroidTheme.typography.title,
                modifier = Modifier.fillMaxWidth().padding(top = 70.dp, bottom = 28.dp),
            )

            Column(Modifier.fillMaxWidth()) {
                Text("사용하실 닉네임을 설정해주세요", style = BetterAndroidTheme.typography.subtitle, color = BetterColors.Gray30)
                Spacer(modifier = Modifier.height(14.dp).fillMaxWidth())
                BetterTextField(
                    value = uistate.nickname,
                    onValueChange = viewModel::inputNickname,
                    isError = !uistate.isValidNickName && uistate.nickname.isNotEmpty(),
                    // helperTextEnabled = uistate.nickname.isNotEmpty(),
                    // helperText = "중복된 닉네임이 있습니다!",
                )
                Spacer(modifier = Modifier.weight(1f))
                BetterButton(
                    text = "완료",
                    type = BetterButtonType.PRIMARY,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uistate.nickname.isNotEmpty(),
                ) {
                }
            }
        }
    }
}
