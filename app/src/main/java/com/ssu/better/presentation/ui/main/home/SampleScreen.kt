package com.ssu.better.presentation.ui.main.home

import BetterCalendar
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ssu.better.entity.study.CheckDay
import com.ssu.better.entity.study.CheckDay.Companion.convertToDayOfWeek
import com.ssu.better.util.AnimatedTransitionDialog
import com.ssu.better.util.CustomDialogPosition
import com.ssu.better.util.customDialogModifier
import com.ssu.better.util.getDaysOfWeek
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SampleScreen(navHostController: NavHostController) {
    var isDialogOpen by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val checkDays = listOf(CheckDay.SAT, CheckDay.MON, CheckDay.SUN).map { it.convertToDayOfWeek() }

    Scaffold() {
        if (isDialogOpen) {
            AnimatedTransitionDialog(
                onDismissRequest = {
                    isDialogOpen = !isDialogOpen
                },
                ANIMATION_DURATION = 500L, // animation enter & exit duration
                DISMISS_DURATION = 1000L, // dialog dismiss duration
                modifier = Modifier.customDialogModifier(CustomDialogPosition.TOP).fillMaxWidth(),
                content = { dialogHelper ->
                    BetterCalendar(
                        onDialogDismiss = {
                            dialogHelper::triggerAnimatedDismiss.invoke() // dialog 닫기
                        },
                        onClickDate = { date ->
                            selectedDate = date
                            coroutineScope.launch {
                                delay(1000L) // 닫기 delay
                                dialogHelper::triggerAnimatedDismiss.invoke()
                            }
                        },
                        selectedDate = selectedDate,
                        isDialogType = true, // 닫기 아이콘 활성화 여부를 위함
                        activateDayList = getDaysOfWeek(LocalDate.now()).filter {
                            checkDays.contains(it.dayOfWeek)
                        },
                        // activateDayList.size >0 일시, 선택 가능 날찌 지정하는 것으로 간주하여 이외의 날짜 클릭 disable됨
                        // LocalDate 형식으로 전달하기 때문에 위와 같은 형식으로 사용하면 해당요일의 체크데이만 가져올 수 있음
                    )
                },
            )
        }

        // 전체화면 사용 시
        BetterCalendar(
            modifier = Modifier.fillMaxSize(),
            onClickDate = { date ->
                selectedDate = date
            },
            selectedDate = selectedDate,
        )
    }
}
