package com.ssu.better.presentation.ui.study.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssu.better.R
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.StudyTask
import com.ssu.better.presentation.component.BetterRoundChip
import com.ssu.better.ui.theme.BetterColors

@Composable
fun StudyChallengeScreen(
    study: Study,
    tasks: ArrayList<StudyTask>,
    onClickChallengeAdd: (StudyTask) -> Unit,
    onClickChallengeApprove: (StudyTask) -> Unit,
    myTask: StudyTask?,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BetterRoundChip(
            modifier = Modifier.padding(top = 20.dp),
            enabled = true,
            text = "${study.taskGroupList.size}회차",
            onClick = {},
        )

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            if (myTask != null) {
                item {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(141.dp)
                            .padding(10.dp),
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(18.dp))
                            Button(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(84.dp),
                                onClick = {
                                    onClickChallengeAdd(myTask)
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BetterColors.Gray00,
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(BetterColors.Primary40),
                                )
                            }
                        }
                    }
                }

                items(tasks.size) { index ->
                    val item = tasks[index]
                    UserChallengeCard(
                        studyTask = item,
                        memberCount = study.memberCount,
                        onClick = onClickChallengeApprove,
                    )
                }
            }
        }
    }
}
