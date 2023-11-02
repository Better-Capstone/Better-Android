package com.ssu.better.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ssu.better.R
import javax.annotation.concurrent.Immutable

val pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
)

val Typography = BetterTypography(
    headline0 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 43.sp,
        letterSpacing = 0.5.sp,
    ),
    headline1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 21.5.sp,
        letterSpacing = 0.sp,
    ),
    headline2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp,
    ),
    headline3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.7.sp,
        letterSpacing = 0.sp,
    ),
    headline4 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp,
    ),
    title = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.4.sp,
    ),

    subtitle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.7.sp,
        letterSpacing = 0.sp,
    ),
    body = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp,
    ),

    option = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 11.9.sp,
        letterSpacing = 0.sp,
    ),
    caption = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp,
    ),

)

val Title = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.4.sp,
)

val Heading0 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 36.sp,
    lineHeight = 43.sp,
    letterSpacing = 0.5.sp,
)

val Heading1 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 21.5.sp,
    letterSpacing = 0.sp,
)

val Heading2 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 19.sp,
    letterSpacing = 0.sp,
)

val Heading3 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 16.7.sp,
    letterSpacing = 0.sp,
)

val Heading4 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 14.3.sp,
    letterSpacing = 0.sp,
)

val Subtitle = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 16.7.sp,
    letterSpacing = 0.sp,
)

val Body = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 14.3.sp,
    letterSpacing = 0.sp,
)

val Caption = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 14.3.sp,
    letterSpacing = 0.sp,
)

val Option = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 11.9.sp,
    letterSpacing = 0.sp,
)

@Immutable
data class BetterTypography(
    val headline0: TextStyle,
    val headline1: TextStyle,
    val headline2: TextStyle,
    val headline3: TextStyle,
    val headline4: TextStyle,
    val title: TextStyle,
    val subtitle: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
    val option: TextStyle,
)
internal val LocalTypography = staticCompositionLocalOf { Typography }
