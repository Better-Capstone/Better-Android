package com.ssu.better.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

enum class BetterButtonType { PRIMARY, DEFAULT, GRAY }

@Composable
fun BetterButton(
    text: String,
    enabled: Boolean = true,
    type: BetterButtonType,
    modifier: Modifier = Modifier,
    textColor: Color = BetterColors.White,
    onClick: () -> Unit,
    typo: TextStyle = BetterAndroidTheme.typography.headline2,
) {
    Button(
        modifier = modifier.padding(vertical = 16.dp),
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            // if (type == BetterButtonType.PRIMARY) BetterColors.Primary50 else BetterColors.Gray90,
            containerColor = when (type) {
                BetterButtonType.DEFAULT -> BetterColors.Gray90
                BetterButtonType.PRIMARY -> BetterColors.Primary50
                else -> BetterColors.Gray00
            },
            contentColor = textColor,
            disabledContainerColor = BetterColors.Gray20,
            disabledContentColor = BetterColors.White,
        ),
        elevation = null,
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else BetterColors.White,
            style = typo,
        )
    }
}
