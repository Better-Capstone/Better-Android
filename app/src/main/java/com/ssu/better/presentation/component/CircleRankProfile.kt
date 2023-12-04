package com.ssu.better.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterColors

@Composable
fun CircleRankProfile(
    modifier: Modifier = Modifier,
    score: Long,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        contentColor = BetterColors.Gray00,
        shadowElevation = 1.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BetterColors.Gray00)
                .padding(6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(
                    id = when (score) {
                        in 0..3999 -> R.drawable.ic_candle_empty
                        in 4000..5999 -> R.drawable.ic_candle
                        in 6000..7999 -> R.drawable.ic_fire_base
                        else -> R.drawable.ic_bonfire
                    },
                ),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
        }
    }
}
