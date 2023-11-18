package com.ssu.better.presentation.ui.main.mypage

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
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRankHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    sealed class UIState {
        object Loading : UIState()
        data class Success(val userRank: UserRank, val studyList: List<Study>) : UIState()
    }

    private val _isNotifyEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isNotifyEnabled: StateFlow<Boolean>
        get() = _isNotifyEnabled

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState>
        get() = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        // TODO API 연결하기
        val testUserRank = UserRank(id = 3, userId = 1, score = 7530, createdAt = "", updatedAt = "")
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
            "제목",
            "설명",
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

        val studyList = arrayListOf(testStudy, testStudy, testStudy)

        viewModelScope.launch {
            delay(2000L)
            _uiState.emit(UIState.Success(testUserRank, studyList))
        }
    }

    fun changeNotify(isNotifyEnabled: Boolean) {
        viewModelScope.launch {
            _isNotifyEnabled.emit(isNotifyEnabled)
        }
    }
}
