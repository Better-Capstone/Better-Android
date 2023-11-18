package com.ssu.better.presentation.ui.study.select_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectCategoryScreen(
    navController: NavController,
    viewModel: SelectCategoryViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.eventSharedFlow.collectLatest {
            when (it) {
                is SelectCategoryViewModel.Event.Finish -> {
                    navController.popBackStack()
                }

                else -> {
                    navController.navigate(Screen.CreateStudy.route)
                }
            }
        }
    }
    SelectCategory(
        onClickFinish = viewModel::onClickFinish,
        categoryList = viewModel.categoryList,
        onClickCategory = viewModel::onClickCategory,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategory(
    onClickFinish: () -> Unit,
    categoryList: List<Category>,
    onClickCategory: (Category) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = BetterColors.Bg)
                    .shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = "카테고리 선택",
                        style = BetterAndroidTheme.typography.subtitle,
                        color = BetterColors.Black,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClickFinish) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxHeight().padding(start = 10.dp, end = 10.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(categoryList.size) { index ->
                    val item = categoryList[index]
                    CategoryItem(category = item, onClick = { onClickCategory(item) })
                }
            }
        }
    }
}
