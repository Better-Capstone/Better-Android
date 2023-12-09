package com.ssu.better.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun SuccessScreen(
    modifier: Modifier,
    message: String = "",
    contentColor: Color = BetterColors.Gray90,

) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        GradientIcon(src = R.drawable.ic_complete, modifier = Modifier.size(120.dp))
        Text(text = message, style = BetterAndroidTheme.typography.headline3, color = contentColor)
    }
}

@Preview
@Composable
fun PreviewSuccessScreen() {
    SuccessScreen(modifier = Modifier.fillMaxSize(), "성공")
}
