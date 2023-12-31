package com.ssu.better.presentation.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val token by viewModel.authToken.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            delay(2000)
            if (token.isNullOrBlank()) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
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
        Box(
            modifier = Modifier
                .fillMaxHeight().fillMaxWidth()
                .background(Color.White),

        ) {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_letter),
                    contentDescription = null,
                    Modifier.width(224.dp),
                )
                Text(text = context.getString(R.string.splash_guide), style = BetterAndroidTheme.typography.headline2, color = Color.Black)
            }
        }
    }
}
