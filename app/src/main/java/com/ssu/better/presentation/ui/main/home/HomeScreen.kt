package com.ssu.better.presentation.ui.main.home

import BetterCalendar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.member.MemberType
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyStatus
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.AnimatedTransitionDialog
import com.ssu.better.util.CustomDialogPosition
import com.ssu.better.util.customDialogModifier
import com.ssu.better.util.noRippleClickable
import com.ssu.better.util.toLocalDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState = homeViewModel.uiState.collectAsStateWithLifecycle()

    var isDialogOpen by remember { mutableStateOf(false) }

    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testMember = Member(1, 1, MemberType.MEMBER, time)
    val testTask = Task(1, 1, time, 1, 1, time, time, "제목")
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val tasks = List(2) { testTask }.toMutableList()
    val testStudy = Study(
        1,
        testUser,
        testCategory,
        "알고리즘 스터디",
        "스터디 설명",
        StudyStatus.INPROGRESS,
        StudyPeriod.EVERYDAY,
        StudyCheckDay.EVERYDAY,
        5,
        1,
        10,
        1500,
        arrayListOf(testMember),
        userRankHistoryList = arrayListOf(testUserRankHistory),
        groupRank = testGroupRank,
        createdAt = "",
    )

    LaunchedEffect(uiState) {
    }

    Scaffold() {
        if (isDialogOpen) {
            AnimatedTransitionDialog(
                onDismissRequest = {
                    isDialogOpen = !isDialogOpen
                },
                ANIMATION_DURATION = 500L, // animation enter & exit duration
                DISMISS_DURATION = 1000L, // dialog dismiss duration
                modifier = Modifier
                    .customDialogModifier(CustomDialogPosition.TOP)
                    .fillMaxWidth(),
                content = { dialogHelper ->
                    BetterCalendar(
                        onDialogDismiss = {
                            dialogHelper::triggerAnimatedDismiss.invoke() // dialog 닫기
                        },
                        onClickDate = { date ->
                            homeViewModel.changeSelectedDate(date)
                            coroutineScope.launch {
                                delay(500L) // 닫기 delay
                                dialogHelper::triggerAnimatedDismiss.invoke()
                            }
                        },
                        selectedDate = uiState.value.selectedDate,
                        isDialogType = true, // 닫기 아이콘 활성화 여부를 위함
                        // activateDayList.size >0 일시, 선택 가능 날찌 지정하는 것으로 간주하여 이외의 날짜 클릭 disable됨
                        // LocalDate 형식으로 전달하기 때문에 위와 같은 형식으로 사용하면 해당요일의 체크데이만 가져올 수 있음
                    )
                },
            )
        }

        Column(
            modifier = Modifier
                .background(BetterColors.Bg)
                .padding(16.dp)
                .padding(bottom = 60.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = uiState.value.selectedDateStr,
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Gray90,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f)
                        .noRippleClickable { isDialogOpen = !isDialogOpen },
                    tint = BetterColors.Primary50,
                )
            }

            LazyColumn() {
                val studyList = List<Study>(3) { testStudy }
                item {
                    studyList.forEach {
//                        StudyTaskCard(
//                            study = it,
//                            baseDate = testTime,
//                            onClickMore = {},
//                            onClickTask = {},
//                        )
                    }
                }
            }
        }
    }
}
