package com.ssu.better.presentation.ui.onboard

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.presentation.ui.login.LoginViewModel
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnBoardScreen(
    navController: NavHostController,
    nickname: String,
    token: String,
    viewModel: OnBoardViewModel = hiltViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val context = LocalContext.current

    val uistate by viewModel.uiState.collectAsStateWithLifecycle()

    val loadingState by viewModel.isLoading.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    val loginViewModel: LoginViewModel = hiltViewModel(navController.getBackStackEntry(Screen.Login.route))

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.inputNickname(nickname)
            viewModel.saveToken(token)

            viewModel.events.collectLatest {
                when (it) {
                    is OnBoardViewModel.OnBoardEvent.NavToMain -> {
                        delay(500)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.OnBoard.route) {
                                inclusive = true
                            }
                        }
                    }

                    is OnBoardViewModel.OnBoardEvent.ShowToast -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .imePadding()
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                String.format(
                    stringResource(R.string.onboard_welcome),
                    loginViewModel.userInfo?.nickname,
                ),
                style = BetterAndroidTheme.typography.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, bottom = 28.dp),
            )

            Column(Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.onboard_guide), style = BetterAndroidTheme.typography.subtitle, color = BetterColors.Gray30)
                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(),
                )
                BetterTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uistate.nickname ?: "",
                    onValueChange = viewModel::inputNickname,
                    isError = !uistate.isValidNickName && uistate.nickname.isNotEmpty(),
                    // helperTextEnabled = uistate.nickname.isNotEmpty(),
                    // helperText = "중복된 닉네임이 있습니다!",
                )
                Spacer(modifier = Modifier.weight(1f))
                BetterButton(
                    text = stringResource(R.string.onboard_complete),
                    type = BetterButtonType.PRIMARY,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loadingState && uistate.nickname.isNotEmpty(),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.postRegisterUser()
                    },
                )
            }
        }
    }
}
