package com.ssu.better.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun GradientProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier
                .progressSemantics(progress),
        ) {
            // Start at 12 o'clock
            val sweep = progress * 360f
            val startAngle = 360f
            drawDeterminateCircularIndicator(startAngle, 360f, stroke)
            drawCircularIndicator(
                startAngle = startAngle,
                sweep = sweep,
                stroke = stroke,
            )
        }
        DisplayText(percent = progress)
    }
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    stroke: Stroke,
) = drawCircularIndicator(startAngle, sweep, BetterColors.White, stroke)

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke,
    )
}

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    rotate(degrees = -90f) {
        drawArc(
            brush = BetterColors.gradation,
            startAngle = startAngle + 90,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke,
        )
    }
}

@Composable
private fun DisplayText(percent: Float) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Text that shows the number inside the circle
        Text(
            text = "${(percent * 100).toInt()}%",
            style = BetterAndroidTheme.typography.headline0,
        )
    }
}
