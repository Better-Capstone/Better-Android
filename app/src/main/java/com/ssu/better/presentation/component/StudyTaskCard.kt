package com.ssu.better.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.ssu.better.util.getIcon
import com.ssu.better.util.toLocalDate
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun StudyTaskCard(
    study: Study,
    baseDate: LocalDate,
    onClickMore: (Study) -> Unit,
    onClickTask: (Task) -> Unit,
) {
    val taskMax = if (study.taskList.size > 5) 4 else study.taskList.size - 1

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = BetterColors.White,
            contentColor = BetterColors.Gray90,
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .offset(y = 8.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painterResource(id = study.category.getIcon()),
                null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 4.dp)
                    .padding(end = 8.dp),
            )
            Text(
                text = study.title,
                style = BetterAndroidTheme.typography.headline3,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
            )
            MoreButton(
                onClick = {
                    onClickMore(study)
                },
            )
        }
        LazyColumn(modifier = Modifier.heightIn(200.dp, 250.dp)) {
            item {
                study.taskList.forEach {
                    TaskItem(
                        modifier = Modifier.fillMaxWidth(),
                        task = it,
                        baseDate = baseDate,
                        onClick = {
                            onClickTask(it)
                        },
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .background(BetterColors.Gray00)
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStudyCard() {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    val testTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val time = "2023-11-28T04:03:15.458Z".toLocalDate()?.atStartOfDay(ZoneOffset.UTC)?.format(DateTimeFormatter.ofPattern(pattern)) ?: ""
    val testUser = User(1, "배현빈", "개발하는 북극곰")
    val testMember = Member(1, 1, MemberType.MEMBER, time)
    val testTask = Task(1, 1, time, 1, 1, time, time, "제목")
    val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
    val testCategory = StudyCategory(1, Category.IT.name)
    val testGroupRank = GroupRank(1, 18000)
    val tasks = List(5) { testTask }.toMutableList()
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
        ArrayList(tasks),
        arrayListOf(testUserRankHistory),
        testGroupRank,
    )
    StudyTaskCard(study = testStudy, baseDate = testTime, onClickMore = {}, onClickTask = {})
}
