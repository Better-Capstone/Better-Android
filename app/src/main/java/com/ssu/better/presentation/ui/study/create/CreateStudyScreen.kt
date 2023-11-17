package com.ssu.better.presentation.ui.study.create

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.user.UserRankName
import com.ssu.better.presentation.component.BetterButton
import com.ssu.better.presentation.component.BetterButtonType
import com.ssu.better.presentation.component.BetterTextField
import com.ssu.better.presentation.ui.study.join.StudyCheckDayCard
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun CreateStudyScreen(
    navHostController: NavHostController,
    viewModel: CreateStudyViewModel = hiltViewModel(),
) {
    val kickCondition = viewModel.kickCondition.collectAsState()
    val title = viewModel.title.collectAsState()
    val description = viewModel.description.collectAsState()
    val checkDay = viewModel.checkDay.collectAsState()
    val period = viewModel.period.collectAsState()
    val minRank = viewModel.minRank.collectAsState()

    CreateStudy(
        onClickFinish = {
            navHostController.popBackStack()
        },
        title = title.value,
        onTitleChanged = viewModel::updateTitle,
        description = description.value,
        onDescriptionChanged = viewModel::updateDescription,
        period = period.value,
        onClickPeriod = viewModel::updatePeriod,
        checkDay = checkDay.value,
        onCheckDayChanged = viewModel::updateCheckDay,
        minRank = minRank.value,
        onMinRankChanged = viewModel::updateMinRank,
        kickCondition = kickCondition.value,
        onKickConditionChanged = viewModel::updateKickCondition,
        onClickComplete = { },
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewCreateStudy() {
    var kickCondition = remember { mutableStateOf("3") }
    val title = remember { mutableStateOf("제목") }
    val description = remember { mutableStateOf("설명") }
    CreateStudy(
        onClickFinish = { },
        title = title.value,
        onTitleChanged = { value -> title.value = value },
        description = description.value,
        onDescriptionChanged = { value -> description.value = value },
        period = StudyPeriod.EVERYDAY,
        onClickPeriod = { },
        checkDay = StudyCheckDay.EVERYDAY,
        onCheckDayChanged = { },
        minRank = UserRankName.CANDLE,
        onMinRankChanged = { },
        kickCondition = kickCondition.value,
        onKickConditionChanged = { value -> kickCondition.value = value },
        onClickComplete = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStudy(
    onClickFinish: () -> Unit,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    period: StudyPeriod,
    onClickPeriod: (StudyPeriod) -> Unit,
    checkDay: StudyCheckDay,
    onCheckDayChanged: (StudyCheckDay) -> Unit,
    minRank: UserRankName,
    onMinRankChanged: (UserRankName) -> Unit,
    kickCondition: String,
    onKickConditionChanged: (String) -> Unit,
    onClickComplete: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = BetterColors.Bg)
                    .shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = "스터디 개설",
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(color = BetterColors.Bg)
                .verticalScroll(scrollState),
        ) {
            BetterTextField(
                modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                value = title,
                hint = "스터디 명",
                onValueChange = onTitleChanged,
            )

            BetterTextField(
                modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                value = description,
                hint = "설명",
                onValueChange = onDescriptionChanged,
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 15.dp, end = 20.dp),
                text = "주기",
                style = BetterAndroidTheme.typography.subtitle,
                color = BetterColors.Black,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                BetterButton(
                    modifier = Modifier.weight(1f),
                    text = "매일",
                    type = if (period == StudyPeriod.EVERYDAY) BetterButtonType.PRIMARY else BetterButtonType.GRAY,
                    textColor = if (period == StudyPeriod.EVERYDAY) BetterColors.White else BetterColors.Black,
                    onClick = {
                        onClickPeriod(StudyPeriod.EVERYDAY)
                    },
                )

                BetterButton(
                    modifier = Modifier.weight(1f),
                    text = "매주",
                    type = if (period == StudyPeriod.WEEKLY) BetterButtonType.PRIMARY else BetterButtonType.GRAY,
                    textColor = if (period == StudyPeriod.WEEKLY) BetterColors.White else BetterColors.Black,
                    onClick = {
                        onClickPeriod(StudyPeriod.WEEKLY)
                    },
                )

                BetterButton(
                    modifier = Modifier.weight(1f),
                    text = "격주",
                    type = if (period == StudyPeriod.BIWEEKLY) BetterButtonType.PRIMARY else BetterButtonType.GRAY,
                    textColor = if (period == StudyPeriod.BIWEEKLY) BetterColors.White else BetterColors.Black,
                    onClick = {
                        onClickPeriod(StudyPeriod.BIWEEKLY)
                    },
                )
            }

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "종료 날짜",
                style = BetterAndroidTheme.typography.subtitle,
                color = BetterColors.Black,
            )

            Row(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_line),
                    contentDescription = "달력 아이콘",
                    tint = BetterColors.Primary40,
                )

                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "10월 1일 (월)",
                    style = BetterAndroidTheme.typography.subtitle,
                    color = BetterColors.Black,
                )
            }

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                text = "반복 요일",
                style = BetterAndroidTheme.typography.subtitle,
                color = BetterColors.Black,
            )

            StudyCheckDayCard(
                checkDay = checkDay,
                onClick = onCheckDayChanged,
                topPadding = 10.dp,
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = "참여 가능 최소 레벨",
                style = BetterAndroidTheme.typography.subtitle,
                color = BetterColors.Black,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(if (minRank == UserRankName.OFF_CANDLE) BetterColors.Primary00 else BetterColors.Gray00)
                        .clickable { onMinRankChanged(UserRankName.OFF_CANDLE) },
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_candle_empty),
                        contentDescription = "꺼진 촛불",
                    )
                }

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(if (minRank == UserRankName.CANDLE) BetterColors.Primary00 else BetterColors.Gray00)
                        .clickable { onMinRankChanged(UserRankName.CANDLE) },
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_candle),
                        contentDescription = "촛불",
                    )
                }

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(if (minRank == UserRankName.FIRE) BetterColors.Primary00 else BetterColors.Gray00)
                        .clickable { onMinRankChanged(UserRankName.FIRE) },
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_fire_base),
                        contentDescription = "불꽃",
                    )
                }

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(if (minRank == UserRankName.BONFIRE) BetterColors.Primary00 else BetterColors.Gray00)
                        .clickable { onMinRankChanged(UserRankName.BONFIRE) },
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_bonfire),
                        contentDescription = "빈 촛불",
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = "자동 퇴출 조건",
                style = BetterAndroidTheme.typography.subtitle,
                color = BetterColors.Black,
            )

            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BetterTextField(
                    modifier = Modifier
                        .width(42.dp)
                        .height(42.dp),
                    value = kickCondition,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = BetterAndroidTheme.typography.body.copy(textAlign = TextAlign.Center),
                    onValueChange = onKickConditionChanged,
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp),
                    text = "회 결석 시",
                    style = BetterAndroidTheme.typography.headline3,
                    color = BetterColors.Black,
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            BetterButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                text = "완료",
                type = BetterButtonType.DEFAULT,
                onClick = onClickComplete,
            )
        }
    }
}
