package com.ssu.better.presentation.ui.main.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.SortOption
import com.ssu.better.entity.study.Study
import com.ssu.better.presentation.component.CircleCategoryButton
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.SearchTextField
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.component.StudyCard
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.getUserRankIcon

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }
    var option by remember { mutableStateOf(SortOption.RANK) }

    val listState = rememberLazyGridState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initView()
    }

    LaunchedEffect(uiState) {
        listState.animateScrollToItem(0)
    }

    LaunchedEffect(option.name) {
        viewModel.sort(option)
        listState.animateScrollToItem(0)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BetterColors.Bg),
        floatingActionButton = {
            AddStudyButton(
                modifier = Modifier
                    .size(50.dp)
                    .offset(y = (-60).dp),
                onClick = {
                    navHostController.navigate(Screen.SelectCategory.route)
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 30.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Column() {
                    Text(text = "${userInfo.nickname}님,", style = BetterAndroidTheme.typography.headline2, color = BetterColors.Gray30)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.search_guide),
                        style = BetterAndroidTheme.typography.headline2,
                        color = BetterColors.Gray70,
                    )
                }

                UserRankProfile(rank = 1)
            }
            SearchTextField(
                value = query,
                onValueChange = { s -> query = s },
                hint = "스터디 이름",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                onClickSearch = { query ->
                    navHostController.navigate(route = Screen.Search.Detail.route + "?query=$query")
                },
            )
            StudyCategoryTab(
                onClickCategory = { category ->
                    navHostController.navigate(route = Screen.Search.Detail.route + "?category=${category.name}") {
                    }
                },
                selectedCategory = Category.ALL,
            )
            when (uiState) {
                is SearchViewModel.SearchUiState.Success -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        StudySortingToggle(
                            option = option,
                            onClick = {
                                option = if (option == SortOption.LATEST) {
                                    SortOption.RANK
                                } else {
                                    SortOption.LATEST
                                }
                            },
                        )
                    }

                    StudyListView(
                        list = (uiState as SearchViewModel.SearchUiState.Success).list,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 30.dp),
                        listState = listState,
                        onClick = { studyId ->
                            navHostController.navigate(Screen.StudyJoin.route + "?studyId=$studyId")
                        },

                    )
                }

                is SearchViewModel.SearchUiState.Loading -> {
                    ShowLoadingAnimation()
                }

                is SearchViewModel.SearchUiState.Empty -> {
                    ErrorScreen(
                        Modifier.fillMaxSize(),
                        stringResource(id = R.string.search_empty),
                    )
                }

                is SearchViewModel.SearchUiState.Fail -> {
                    ErrorScreen(
                        Modifier.fillMaxSize(),
                        (uiState as SearchViewModel.SearchUiState.Fail).message,
                    )
                }
            }
        }
    }
}

@Composable
fun AddStudyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = BetterColors.Primary50,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(3.dp),
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            tint = BetterColors.White,
        )
    }
}

@Composable
fun StudyListView(
    list: List<Study>,
    modifier: Modifier = Modifier,
    listState: LazyGridState = rememberLazyGridState(),
    onClick: (Long) -> Unit,
) {
    LazyVerticalGrid(
        state = listState,
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            itemsIndexed(list) { idx, study ->
                StudyCard(
                    modifier = Modifier.padding(),
                    study = study,
                    onClick = onClick,
                )
            }
        },
    )
}

@Composable
fun StudyCategoryTab(
    onClickCategory: (Category) -> Unit,
    selectedCategory: Category? = null,
) {
    LazyRow() {
        item() {
            CircleCategoryButton(
                category = Category.ALL,
                onClick = { onClickCategory(Category.ALL) },
                selected = selectedCategory == Category.ALL,
                modifier = Modifier.padding(10.dp),
            )
            Category.values().forEach {
                if (Category.ALL != it) {
                    CircleCategoryButton(
                        category = it,
                        onClick = { onClickCategory(it) },
                        selected = selectedCategory == it,
                        modifier = Modifier.padding(10.dp),

                    )
                }
            }
        }
    }
}

@Composable
fun StudySortingToggle(
    option: SortOption,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onClick()
        },
    ) {
        Text(
            text = SortOption.RANK.kor,
            color = if (option == SortOption.RANK) BetterColors.Gray90 else BetterColors.Gray10,
            style = BetterAndroidTheme.typography.headline3,
        )
        Text(text = "|", Modifier.padding(horizontal = 2.dp))
        Text(
            text = SortOption.LATEST.kor,
            color = if (option == SortOption.LATEST) BetterColors.Gray90 else BetterColors.Gray10,
            style = BetterAndroidTheme.typography.headline3,
            modifier = Modifier.padding(end = 2.dp),
        )
        Icon(painter = painterResource(id = R.drawable.ic_sort), contentDescription = null, modifier = Modifier.size(12.dp))
    }
}

@Composable
fun UserRankProfile(
    rank: Long,
) {
    Surface(
        shape = CircleShape,
        shadowElevation = 2.dp,
        color = BetterColors.Primary00,

    ) {
        val image = when (rank) {
            else -> getUserRankIcon(rank)
        }
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
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
