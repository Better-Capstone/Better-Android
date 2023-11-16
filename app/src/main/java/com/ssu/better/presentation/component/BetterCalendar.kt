
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors
import com.ssu.better.util.conditional
import com.ssu.better.util.dateFormat
import com.ssu.better.util.noRippleClickable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val DAY_SIZE = 35.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BetterCalendar(
    modifier: Modifier = Modifier,
    config: BetterCalendarConfig = BetterCalendarConfig(),
    selectedDate: LocalDate? = null,
    onClickDate: (LocalDate) -> Unit,
    activateDayList: List<LocalDate> = emptyList(),
    isDialogType: Boolean = false,
    onDialogDismiss: (() -> Unit)? = null,
) {
    var dateState by remember { mutableStateOf(selectedDate ?: LocalDate.now()) }
    var currentDate by remember { mutableStateOf(selectedDate ?: LocalDate.now()) }

    val initialPage = (dateState.year - config.yearRange.first) * 12 + dateState.monthValue - 1
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { (config.yearRange.last - config.yearRange.first) * 12 },
    )
    var currentPage by remember { mutableIntStateOf(initialPage) }

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentDate = currentDate.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    LaunchedEffect(selectedDate) {
        if (selectedDate != null) dateState = selectedDate
    }

    val calendarModifier = if (isDialogType) {
        Modifier.height(385.dp).clip(shape = RoundedCornerShape(10.dp)).background(BetterColors.White)
    } else {
        modifier
    }

    Column(
        modifier = calendarModifier,
    ) {
        val headerText = currentDate.dateFormat("yyyy년 M월")
        val pageCount = (config.yearRange.last - config.yearRange.first) * 12

        CalendarHeader(
            modifier = Modifier.padding(vertical = 25.dp),
            text = headerText,
            onDismiss = onDialogDismiss,
            isDialogType = isDialogType,
        )

        HorizontalPager(
            modifier = calendarModifier.fillMaxHeight().padding(bottom = 10.dp),
            verticalAlignment = Alignment.Top,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = pageCount,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                Orientation.Horizontal,
            ),
            pageContent = { page ->
                val date = LocalDate.of(
                    config.yearRange.first + page / 12,
                    page % 12 + 1,
                    1,
                )
                if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                    CalendarMonthItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        currentDate = date,
                        selectedDate = dateState,
                        onSelectedDate = onClickDate,
                        activateDayList = activateDayList,
                    )
                }
            },
        )
    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    text: String,
    onDismiss: (() -> Unit)?,
    isDialogType: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = BetterAndroidTheme.typography.subtitle,
            color = BetterColors.Gray90,
        )

        if (isDialogType) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = null,
                modifier = Modifier.size(24.dp).noRippleClickable { onDismiss?.invoke() },
                tint = BetterColors.Primary50,
            )
        }
    }
}

@Composable
fun CalendarMonthItem(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    activateDayList: List<LocalDate> = emptyList(),
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeekLabel()
        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(7),
        ) {
            for (i in 0 until firstDayOfWeek) {
                item {
                    Box(
                        modifier = Modifier
                            .size(DAY_SIZE)
                            .padding(top = 10.dp),
                    )
                }
            }
            items(days) { day ->
                val date = currentDate.withDayOfMonth(day)
                val isSelected = remember(selectedDate) {
                    selectedDate.compareTo(date) == 0
                }
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    date = date,
                    isToday = date == LocalDate.now(),
                    isSelected = isSelected,
                    onSelectedDate = onSelectedDate,
                    isClickable = if (activateDayList.isEmpty()) true else activateDayList.contains(date),
                    activateDate = activateDayList.contains(date),
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    isClickable: Boolean,
    onSelectedDate: (LocalDate) -> Unit,
    activateDate: Boolean = false,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(DAY_SIZE)
            .clip(shape = RoundedCornerShape(5.dp))
            .conditional(isToday) {
                background(BetterColors.Primary00)
            }.conditional(activateDate) {
                background(BetterColors.White).border(1.dp, color = BetterColors.Primary40, shape = RoundedCornerShape(5.dp))
            }
            .conditional(isSelected) {
                background(BetterColors.Primary20)
            }
            .conditional(!isToday && !isSelected && !activateDate) {
                background(BetterColors.Gray00)
            }.conditional(isClickable) {
                noRippleClickable { onSelectedDate(date) }
            },

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        val textColor = if (isSelected) BetterColors.White else if (activateDate) BetterColors.Primary40 else BetterColors.Gray90
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
            style = BetterAndroidTheme.typography.body,
            color = textColor,
        )
    }
}

@Composable
fun DayOfWeekLabel(
    modifier: Modifier = Modifier,
) {
    val days = arrayListOf<DayOfWeek>()
    days.add(DayOfWeek.SUNDAY)
    days.addAll(DayOfWeek.values().slice(0..5))

    Row(modifier.padding(bottom = 10.dp)) {
        days.forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                style = BetterAndroidTheme.typography.body,
                textAlign = TextAlign.Center,
            )
        }
    }
}

data class BetterCalendarConfig(
    val yearRange: IntRange = IntRange(1990, 2100),
    val locale: Locale = Locale.KOREAN,
)
