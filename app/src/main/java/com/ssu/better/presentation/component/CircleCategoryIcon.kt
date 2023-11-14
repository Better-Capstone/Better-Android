package com.ssu.better.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.study.Category
import com.ssu.better.presentation.utils.getCategoryIcon
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun CircleCategoryButton(
    category: Category,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = CircleShape,
            onClick = onClick,
            shadowElevation = 2.dp,
            color = if (selected) BetterColors.Primary00 else BetterColors.Gray00,
        ) {
            Box(
                modifier = Modifier.padding(9.dp),
            ) {
                if (Category.ALL == category) {
                    Image(
                        painterResource(id = getCategoryIcon(category)),
                        null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),

                    )
                } else {
                    GradientIcon(
                        src = getCategoryIcon(category),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                    )
                }
            }
        }

        Text(text = category.kor, style = BetterAndroidTheme.typography.caption, modifier = Modifier.padding(top = 12.dp))
    }
}
