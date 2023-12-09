package com.ssu.better.presentation.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetGroupRankHistoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.entity.study.GroupRankHistory
import com.ssu.better.entity.study.Study
import com.ssu.better.util.getHttpErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val userPrefManager: UserPrefManager,
    private val getStudyUseCase: GetStudyUseCase,
    private val getGroupRankHistoryUseCase: GetGroupRankHistoryUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReportDetailUiState>(ReportDetailUiState.Loading)
    val uiState get() = _uiState

    fun getGroupRankHistory(studyId: Long, historyId: Long) {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest { pref ->
                pref?.let {
                    getStudyUseCase.getStudy(studyId).combine(
                        getGroupRankHistoryUseCase.getGroupRankHistory(studyId),
                        transform = { study, historys ->
                            val history = historys.find { it.groupRankHistoryId == historyId }
                            if (history == null) {
                                ReportDetailUiState.Fail("잘못된 히스토리 접근")
                            } else {
                                val userChallenge = history.challengeList.find {
                                    it.user.userId == pref.id && it.approveMember.size >= (
                                        history
                                            .totalNumber / 2
                                        )
                                }

                                ReportDetailUiState.Success(
                                    history = historys,
                                    userScore = if (history.challengeList.isEmpty() || userChallenge == null) 0 else 100,
                                    historyCount = historys.size,
                                    study = study,
                                )
                            }
                        },
                    ).catch {
                        _uiState.emit(ReportDetailUiState.Fail((it as HttpException).getHttpErrorMsg()))
                    }.collectLatest {
                        _uiState.emit(it)
                    }
                }
            }
        }
    }

    fun calculateReward(history: GroupRankHistory, userScore: Int): Int {
        var score = if (userScore == 0) 0 else 20
        if (history.participantsNumber == history.totalNumber) score += history.participantsNumber * 5
        return score
    }
}

sealed class ReportDetailUiState {
    data class Success(val history: ArrayList<GroupRankHistory>, val historyCount: Int, val userScore: Int, val study: Study) : ReportDetailUiState()
    object Loading : ReportDetailUiState()
    data class Fail(val message: String) : ReportDetailUiState()
}
