package com.ssu.better.presentation.ui.main.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.SortOption
import com.ssu.better.presentation.component.SearchTextField
import com.ssu.better.presentation.navigation.Screen
import com.ssu.better.ui.theme.BetterAndroidTheme

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
    val option = remember { mutableStateOf(SortOption.LATEST) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val listState = rememberLazyGridState()

    val studyList by viewModel.studyList.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(studyList) {
        listState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        navHostController.navigate(route = Screen.Search.Main.route) {
                            popUpTo(route = Screen.Search.Detail.route) {
                                inclusive = true
                            }
                        }
                    },
            )
            SearchTextField(
                value = query.value ?: "",
                onValueChange = { s -> query.value = s },
                hint = "스터디 이름",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                onClickSearch = {
                    query.value = it
                    Toast.makeText(context, "search" + it, Toast.LENGTH_SHORT).show()
                    keyboardController?.hide()
                },
            )
        }

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(text = "스터디 카테고리", style = BetterAndroidTheme.typography.headline3, modifier = Modifier.padding(vertical = 12.dp))
            StudyCategoryTab(onClickCategory = {
                    selected ->
                category.value = selected
            }, selectedCategory = category.value,)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            StudySortingToggle(
                option = option.value,
                onClick = {
                    if (option.value == SortOption.LATEST) {
                        option.value = SortOption.RANK
                    } else {
                        option.value = SortOption.LATEST
                    }
                    viewModel.sort(option.value)
                },
            )
        }

        StudyListView(
            list = studyList,
            modifier = Modifier.fillMaxSize(),
            listState = listState,
        )
    }
}
