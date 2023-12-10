package com.ssu.better.presentation.ui.main.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.SortOption
import com.ssu.better.presentation.component.ErrorScreen
import com.ssu.better.presentation.component.SearchTextField
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchDetailScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
    category: Category = Category.ALL,
    query: String? = null,

) {
    val query = remember { mutableStateOf(query) }
    val category = remember { mutableStateOf(category) }
    var option by remember { mutableStateOf(SortOption.LATEST) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val listState = rememberLazyGridState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadListWithCategory(category.value)
    }

    LaunchedEffect(category.value) {
        if (category.value != Category.ALL && query.value.isNullOrBlank()) {
            viewModel.loadListWithCategory(category.value)
        } else {
            viewModel.loadListWithQuery(query.value ?: "", category.value)
        }
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
                    .offset(y = -10.dp),
                onClick = {
                    navHostController.navigate(Screen.SelectCategory.route)
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BetterColors.Bg)
                .padding(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navHostController.popBackStack()
                        },
                )
                SearchTextField(
                    value = query.value ?: "",
                    onValueChange = { s -> query.value = s },
                    hint = "스터디 이름",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                    onClickSearch = {
                        query.value = it
                        keyboardController?.hide()
                        viewModel.loadListWithQuery(it, category.value)
                    },
                )
            }

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = "스터디 카테고리", style = BetterAndroidTheme.typography.headline3, modifier = Modifier.padding(vertical = 12.dp))
                StudyCategoryTab(
                    onClickCategory = { selected ->
                        category.value = selected
                    },
                    selectedCategory = category.value,
                )
            }

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
                        modifier = Modifier.fillMaxSize(),
                        listState = listState,
                        onClick = { studyId -> navHostController.navigate(Screen.StudyJoin.route + "?studyId=$studyId") },
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
