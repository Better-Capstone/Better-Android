package com.ssu.better.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme

@Composable
fun ErrorScreen(
    modifier: Modifier,
    message: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_warning), contentDescription = null, modifier = Modifier.size(120.dp))
        Text(
            text = message,
            style = BetterAndroidTheme.typography.headline3,
        )
    }
}
