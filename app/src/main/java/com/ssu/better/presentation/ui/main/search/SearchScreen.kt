package com.ssu.better.presentation.ui.main.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyStatus
import com.ssu.better.entity.user.User
import com.ssu.better.presentation.component.CircleCategoryButton
import com.ssu.better.presentation.component.SearchTextField
import com.ssu.better.presentation.component.StudyCard
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun SearchScreen(navHostController: NavHostController) {
    val query = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 30.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp),
        ) {
            Column() {
                Text(text = "사용자" + ",님", style = BetterAndroidTheme.typography.headline2, color = BetterColors.Gray30)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "어떤 스터디를 찾고 신가요?", style = BetterAndroidTheme.typography.headline2, color = BetterColors.Gray70)
            }

            UserRankProfile(rank = 1)
        }
        SearchTextField(
            value = query.value,
            onValueChange = { s -> query.value = s },
            hint = "스터디 이름",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
        )
        StudyCategoryTab()
        StudyListView(
            list = List<Study>(20) {
                Study(
                    studyId = 1,
                    status = StudyStatus.END,
                    title = "알고리즘 스터디",
                    owner = User(id = 333, name = "ejfie", nickname = "user"),
                    category = StudyCategory(1, "HEALTH"),
                    period = StudyPeriod.BIWEEKLY,
                    checkDay = StudyCheckDay.FRI,
                    maximumCount = 29,
                    minRank = 1,
                    memberList = arrayListOf(),
                    taskList = arrayListOf(),
                    userRankHistoryList = arrayListOf(),
                    kickCondition = 3,
                    groupRank = GroupRank(1, 1),
                    description = "",
                    memberCount = 8,
                )
            },
        )
    }
}

@Composable
fun StudyListView(
    list: List<Study>,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            itemsIndexed(list) { idx, study ->
                StudyCard(
                    modifier = Modifier.padding(),
                    study = study.copy(groupRank = GroupRank(study.groupRank.groupRankId, study.groupRank.score + idx)),
                )
            }
        },
    )
}

@Composable
fun StudyCategoryTab() {
    LazyRow() {
        item() {
            CircleCategoryButton(category = Category.ALL, onClick = { /*TODO*/ }, selected = true, modifier = Modifier.padding(10.dp))
            Category.values().forEach {
                CircleCategoryButton(category = it, onClick = { /*TODO*/ }, selected = false, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun UserRankProfile(
    rank: Int,
) {
    Surface(
        shape = CircleShape,
        shadowElevation = 2.dp,
        color = BetterColors.Primary00,

    ) {
        val image = when (rank) {
            else -> R.drawable.ic_study_fire
        }
        Box(
            modifier = Modifier.width(50.dp).height(50.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painterResource(id = image),
                null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp),
                contentScale = ContentScale.Fit,

            )
        }
    }
}
