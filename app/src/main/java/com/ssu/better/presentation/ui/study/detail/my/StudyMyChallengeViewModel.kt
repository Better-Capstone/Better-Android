package com.ssu.better.presentation.ui.study.detail.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.study.GetStudyTaskListUseCase
import com.ssu.better.entity.challenge.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyMyChallengeViewModel @Inject constructor(
    private val userPrefManager: UserPrefManager,
    private val getStudyTaskListUseCase: GetStudyTaskListUseCase,
) : ViewModel() {
    private val _uistate = MutableStateFlow<MyChallengeUiState>(MyChallengeUiState.Loading)
    val uistate get() = _uistate

    fun getStudyChallenges(studyId: Long) {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                it?.let { pref ->
                    getStudyTaskListUseCase.getStudyTaskList(studyId).collectLatest {
                        val challenges = it.filter { it.user.userId == pref.id }.mapNotNull { it.challenge }
                        if (challenges.isEmpty()) {
                            _uistate.emit(MyChallengeUiState.Empty)
                        } else {
                            _uistate.emit(MyChallengeUiState.Success(challenges))
                        }
                    }
                }
            }
        }
    }

    sealed class MyChallengeUiState {
        object Loading : MyChallengeUiState()
        data class Success(val challenges: List<Challenge>) : MyChallengeUiState()
        object Empty : MyChallengeUiState()
    }
}
