package com.ssu.better.presentation.ui.study.detail.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.study.GetGroupRankHistoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.study.GetStudyUserListUseCase
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
    private val getStudyUserListUseCase: GetStudyUserListUseCase,
) : ViewModel() {

    private val _uistate = MutableStateFlow<StudyDetailMyUiState>(StudyDetailMyUiState.Loading)
    val uistate get() = _uistate

    fun getStudyInfo(studyId: Long) {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                it?.let { userPref ->
                    combine(
                        getStudyUseCase.getStudy(studyId),
                        getGroupRankHistoryUseCase.getGroupRankHistory(studyId),
                        getStudyUserListUseCase.getStudyUserList(studyId),
                        ::Triple,
                    ).collectLatest {
                        val study = it.first
                        val historys = it.second
                        val memebers = it.third

                        val kickCount = memebers.find { user -> user.userId == userPref.id }?.let { myStudys ->
                            study.memberList.find { myStudys.memberList.contains(it) }?.kickCount ?: 0
                        } ?: 0
                        val userChallenges =
                            historys.flatMap { it.challengeList }.filter { h -> h.user.userId == userPref.id }
                        val challengeCount = userChallenges.size
                        val percent = userChallenges.count() * 100 / (
                            study.taskGroupList
                                .size
                            )
                        _uistate.emit(StudyDetailMyUiState.Success(study, userPref.nickname, percent, challengeCount, kickCount))
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
