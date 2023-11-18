package com.ssu.better.presentation.ui.study.select_category

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssu.better.entity.study.Category
import com.ssu.better.presentation.component.GradientIcon
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.getCategoryIcon

@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val color = if (pressed) BetterColors.Gray00 else BetterColors.White
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        interactionSource = interactionSource,
    ) {
        Box(modifier = Modifier.aspectRatio(1f).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GradientIcon(
                    src = getCategoryIcon(category),
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = category.name,
                    style = BetterAndroidTheme.typography.headline3,
                    color = BetterColors.Primary50,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryItem() {
    CategoryItem(category = Category.IT) {
    }
}
