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
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.getUserRankIcon

@Composable
fun CircleRankProfile(
    modifier: Modifier = Modifier,
    score: Int,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        contentColor = BetterColors.Gray00,
        shadowElevation = 1.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize().background(BetterColors.Gray00).padding(6.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(
                    id = getUserRankIcon(score),
                ),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
        }
    }
}
