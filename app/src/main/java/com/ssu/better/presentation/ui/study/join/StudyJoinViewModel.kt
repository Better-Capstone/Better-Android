package com.ssu.better.presentation.ui.study.join

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyJoinViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    sealed class UIState {
        object Loading : UIState()
        data class Success(val study: Study) : UIState()
    }

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState>
        get() = _uiState

    private val _hour: MutableStateFlow<String> = MutableStateFlow("")
    val hour: StateFlow<String>
        get() = _hour

    private val _minute: MutableStateFlow<String> = MutableStateFlow("")
    val minute: StateFlow<String>
        get() = _minute

    private val _isAm: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAm: StateFlow<Boolean>
        get() = _isAm

    init {
        load()
    }

    fun load() {
        val testUser = User(1, "배현빈", "개발하는 북극곰")
        val testMember = Member(1, 1, MemberType.MEMBER, "")
        val testTask = Task(1, 1, "", 1, 1, "", "", "제목")
        val testUserRankHistory = UserRankHistory(1, 1, 1, 1, 1700, "100점 추가")
        val testCategory = StudyCategory(1, Category.IT.name)
        val testGroupRank = GroupRank(1, 18000)
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
            arrayListOf(testTask),
            arrayListOf(testUserRankHistory),
            testGroupRank,
        )

        viewModelScope.launch {
            delay(2000L)
            _uiState.emit(UIState.Success(testStudy))
        }
    }

    fun updateHour(hour: String) {
        viewModelScope.launch {
            _hour.emit(hour)
        }
    }

    fun updateMinute(minute: String) {
        viewModelScope.launch {
            _minute.emit(minute)
        }
    }

    fun updateIsAm(isAm: Boolean) {
        viewModelScope.launch {
            _isAm.emit(isAm)
        }
    }

    fun join() {
    }
}
