package com.ssu.better.presentation.ui.study.detail.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.study.GetGroupRankHistoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListByUserUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.entity.study.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyDetailMyHomeViewModel @Inject constructor(
    private val userPrefManager: UserPrefManager,
    private val getStudyUseCase: GetStudyUseCase,
    private val getGroupRankHistoryUseCase: GetGroupRankHistoryUseCase,
    private val getStudyListByUserUseCase: GetStudyListByUserUseCase,
) : ViewModel() {

    private val _uistate = MutableStateFlow<StudyDetailMyUiState>(StudyDetailMyUiState.Loading)
    val uistate get() = _uistate

    fun getStudyInfo(studyId: Long) {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                it?.let { userPref ->
                    getStudyUseCase.getStudy(studyId).combine(
                        getGroupRankHistoryUseCase.getGroupRankHistory(studyId),
                    ) { study, historys ->

                        val kickCount = study.memberList.find { it.memberId == userPref.id }?.kickCount ?: 0
                        val userChallenges = historys.flatMap { it.challengeList }.filter { it.user.userId == userPref.id }
                        val challengeCount = userChallenges.count()
                        val percent = userChallenges.map { it.approveMember.size >= study.memberCount / 2 }.count() / (
                            study.taskGroupList
                                .size
                            ) * 100
                        StudyDetailMyUiState.Success(study, userPref.nickname, percent, challengeCount, kickCount)
                    }.collectLatest {
                        _uistate.emit(it)
                    }
                }
            }
        }
    }

    sealed class StudyDetailMyUiState {
        object Loading : StudyDetailMyUiState()
        data class Success(
            val study: Study,
            val nickname: String,
            val percent: Int,
            val challengeCount: Int,
            val kickCount: Int,
        ) : StudyDetailMyUiState()
    }
}
