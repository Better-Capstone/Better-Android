package com.ssu.better.presentation.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getGroupRankHistoryUseCase: GetGroupRankHistoryUseCase,
    private val getStudyUseCase: GetStudyUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReportUiState>(ReportUiState.Loading)
    val uiState get() = _uiState

    fun getGroupRankHistory(studyId: Long) {
        viewModelScope.launch {
            getStudyUseCase.getStudy(studyId).zip(
                other = getGroupRankHistoryUseCase.getGroupRankHistory(studyId),
                transform = { study, history ->
                    ReportUiState.Success(history, study)
                },
            ).catch {
                _uiState.emit(ReportUiState.Fail((it as HttpException).getHttpErrorMsg()))
            }.collectLatest {
                Timber.i(it.toString())
                _uiState.emit(it)
            }
        }
    }
}

sealed class ReportUiState {
    data class Success(val list: List<GroupRankHistory>, val study: Study) : ReportUiState()
    object Loading : ReportUiState()
    data class Fail(val message: String) : ReportUiState()
}
