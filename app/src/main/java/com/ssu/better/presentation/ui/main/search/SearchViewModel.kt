package com.ssu.better.presentation.ui.main.search

import androidx.lifecycle.ViewModel
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.SortOption
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyCategory
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyStatus
import com.ssu.better.entity.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val list = List<Study>(20) {
        Study(
            studyId = 1,
            status = StudyStatus.END,
            title = "알고리즘 스터디",
            owner = User(id = 333, name = "ejfie", nickname = "user"),
            category = StudyCategory(1, "HEALTH"),
            period = StudyPeriod.BIWEEKLY,
            checkDay = StudyCheckDay.FRI,
            maximumCount = 29,
            minRank = 1,
            memberList = arrayListOf(),
            taskList = arrayListOf(),
            userRankHistoryList = arrayListOf(),
            kickCondition = 3,
            groupRank = GroupRank(1, 1),
            description = "",
            memberCount = 8,
        )
    }.toMutableList().mapIndexed { idx, study ->
        study.copy(
            title = study.title + "$idx",
            memberCount = study.maximumCount + (idx % 4 + 1),
            groupRank = GroupRank((idx % 4 + 1).toLong(), study.groupRank.score + (idx % 4 + 1)),
        )
    }

    private val _studyList = MutableStateFlow<List<Study>>(list)
    val studyList get() = _studyList

    fun loadList() {
    }

    fun sort(option: SortOption) {
        if (option == SortOption.LATEST) {
            _studyList.update { studyList.value.toList().sortedBy { it.title } }
        } else {
            _studyList.update { studyList.value.toList().sortedByDescending { it.groupRank.score } }
        }
    }
}
