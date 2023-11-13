package com.ssu.better.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun BetterTextBox(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    width: Dp,
    height: Dp,
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(backgroundColor)
            .clip(RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = BetterAndroidTheme.typography.body,
        )
    }
}

@Preview
@Composable
fun PreviewBetterTextBox() {
    BetterTextBox(
        text = "월",
        backgroundColor = BetterColors.Gray00,
        textColor = BetterColors.Gray90,
        width = 32.dp,
        height = 32.dp,
    )
}
