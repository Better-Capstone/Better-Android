package com.ssu.better.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
class BetterColors {
    companion object {
        val Primary70 = Color(0xFFC20000)
        val Primary60 = Color(0xFFD40506)
        val Primary50 = Color(0xFFFF0000)
        val Primary40 = Color(0xFFFF2424)
        val Primary30 = Color(0xFFFF4747)
        val Primary20 = Color(0xFFFF6B6B)
        val Primary10 = Color(0xFFFFA5A5)
        val Primary00 = Color(0xFFFFF3F3)

        val Black = Color(0xFF101010)
        val Gray90 = Color(0xFF131313)
        val Gray70 = Color(0xFF2B2B2B)
        val Gray50 = Color(0xFF373737)
        val Gray30 = Color(0xFF6C6C6C)
        val Gray20 = Color(0xFFC9C9C9)
        val Gray10 = Color(0xFFE3E3E3)
        val Gray00 = Color(0xFFF3F3F3)
        val TextGray = Color(0xFF808080)

        val Bg = Color(0xFFF9F9F9)
        val White = Color(0xFFFDFDFD)

        val gradation = Brush.verticalGradient(
            listOf(Color(0XFFFF2424), Color(0XFFFF7772)),
        )
    }
}
