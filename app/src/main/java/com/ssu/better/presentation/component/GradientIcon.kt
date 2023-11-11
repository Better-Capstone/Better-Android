package com.ssu.better.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ssu.better.ui.theme.BetterColors

@Composable
fun GradientIcon(src: Int, modifier: Modifier) {
    Icon(
        contentDescription = null,
        imageVector = ImageVector.vectorResource(src),
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        BetterColors.gradation,
                        blendMode = BlendMode.SrcAtop,
                    )
                }
            },
    )
}
