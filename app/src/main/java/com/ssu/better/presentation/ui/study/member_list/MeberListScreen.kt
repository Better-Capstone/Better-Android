package com.ssu.better.presentation.ui.study.member_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.presentation.component.ShowLoadingAnimation
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun MemberListScreen(
    navHostController: NavHostController,
    title: String,
    studyId: Long,
    viewModel: MemberListViewModel = hiltViewModel(),
) {
    val event by viewModel.event.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.load(studyId)
    }
    MemberListContent(
        onClickFinish = {
            navHostController.popBackStack()
        },
        title = title,
        event = event,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberListContent(
    onClickFinish: () -> Unit,
    title: String,
    event: MemberListViewModel.MemberListEvent,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = title,
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
        Column(modifier = Modifier.padding(paddingValues)) {
            when (event) {
                is MemberListViewModel.MemberListEvent.Load -> {
                    ShowLoadingAnimation()
                }

                is MemberListViewModel.MemberListEvent.Success -> {
                    MemberList(userList = event.userList)
                }
            }
        }
    }
}

@Composable
fun MemberList(userList: ArrayList<StudyUser>) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.participate_member),
            textAlign = TextAlign.Center,
            style = BetterAndroidTheme.typography.headline2
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(userList.size) { index ->
                val item = userList[index]
                StudyUserItem(user = item)
            }
        }
    }
}
