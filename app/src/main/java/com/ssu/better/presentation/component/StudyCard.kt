package com.ssu.better.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.Study
import com.ssu.better.util.getCategoryIcon
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyCard(
    study: Study,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Surface(
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.clickable {
            if (onClick != null) {
                onClick()
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 17.dp, horizontal = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StudyCardChip(text = study.period.kor)
                Spacer(modifier = Modifier.width(4.dp))
                StudyCardChip(text = "${study.maximumCount}회차")
            }

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .width(12.dp)
                        .height(12.dp),
                    tint = BetterColors.Gray20,
                )
                Text(
                    text = "${study.memberCount}/${study.maximumCount}",
                    style = BetterAndroidTheme.typography.option,
                    color = BetterColors.Gray20,
                    maxLines = 1,
                )
            }
            Text(
                text = if (study.title.length > 10) {
                    study.title.slice(0..9) + "..."
                } else {
                    study.title
                },
                style = BetterAndroidTheme.typography.headline2,
            )
            Row(
                modifier = Modifier.padding(bottom = 30.dp, top = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GradientIcon(src = R.drawable.ic_study_fire, modifier = Modifier.width(20.dp).height(20.dp))
                Text(text = "${study.groupRank.score}", style = BetterAndroidTheme.typography.title)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${
                    when (study.minRank) {
                        1 -> "꺼진촛불"
                        2 -> "촛불"
                        3 -> "불꽃"
                        else -> "모닥불"
                    }
                    }이상",
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Gray20,
                )

                GradientIcon(src = getCategoryIcon(Category.valueOf(study.category.name)), modifier = Modifier.width(20.dp).height(20.dp))
            }
        }
    }
}

@Composable
fun StudyCardChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .background(color = BetterColors.Primary00, shape = RoundedCornerShape(3.dp))
            .padding(vertical = 3.dp, horizontal = 5.dp),
        color = BetterColors.Primary00,
    ) {
        Text(
            text = text,
            color = BetterColors.Primary50,
            style = BetterAndroidTheme.typography.subtitle,
        )
    }
}
