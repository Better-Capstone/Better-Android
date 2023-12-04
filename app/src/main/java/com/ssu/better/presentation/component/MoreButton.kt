package com.ssu.better.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    text: String = "더보기",
    onClick: () -> Unit,
) {
    OutlinedButton(
        border = BorderStroke(1.dp, BetterColors.Primary50),
        modifier = modifier.defaultMinSize(
            minWidth = ButtonDefaults.MinWidth,
            minHeight = 10.dp,
        ),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BetterColors.White,
            contentColor = BetterColors.Primary50,
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(end = 2.dp),
                color = BetterColors.Primary50,
                style = BetterAndroidTheme.typography.body,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,

                modifier = Modifier.size(12.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewMoreButton() {
    MoreButton {}
}
