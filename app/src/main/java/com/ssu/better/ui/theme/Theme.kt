package com.ssu.better.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@Composable
fun BetterAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    typography: BetterTypography = BetterAndroidTheme.typography,
    content: @Composable () -> Unit,

) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalContentColor provides BetterColors.White,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}
val DarkColors = darkColorScheme(
    primary = BetterColors.Primary50,
    onPrimary = BetterColors.White,
    secondary = BetterColors.Primary40,
    onSecondary = BetterColors.White,
    tertiary = BetterColors.Gray90,
    onTertiary = BetterColors.White,
    background = BetterColors.Bg,
    surface = BetterColors.White,
    onSurface = BetterColors.Black,
    error = BetterColors.Primary50,
)

val LightColors = lightColorScheme(
    primary = BetterColors.Primary50,
    onPrimary = BetterColors.White,
    secondary = BetterColors.Primary40,
    onSecondary = BetterColors.White,
    tertiary = BetterColors.Gray90,
    onTertiary = BetterColors.White,
    background = BetterColors.Bg,
    surface = BetterColors.White,
    onSurface = BetterColors.Black,
    error = BetterColors.Primary50,
)

object BetterAndroidTheme {
    val typography: BetterTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
