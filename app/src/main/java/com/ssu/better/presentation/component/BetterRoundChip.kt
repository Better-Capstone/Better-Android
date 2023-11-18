package com.ssu.better.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.noRippleClickable

@Composable
fun BetterRoundChip(
    enabled: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)).noRippleClickable {
            if (enabled) onClick()
        }.background(if (enabled) BetterColors.Primary00 else BetterColors.Gray10),
    ) {
        Text(
            text = text,
            color = if (enabled) BetterColors.Primary60 else BetterColors.Bg,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
            style = BetterAndroidTheme.typography.headline4,
        )
    }
}
