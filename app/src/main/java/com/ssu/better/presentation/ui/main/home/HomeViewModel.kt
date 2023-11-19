package com.ssu.better.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.domain.usecase.study.GetStudyListByUserUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.util.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStudyListByUserUseCase: GetStudyListByUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState(LocalDate.now()))
    val uiState get() = _uiState

    fun changeSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun loadStudyTaskList(date: LocalDate) {
        viewModelScope.launch {
            try {
                getStudyListByUserUseCase.getStudyListByUser(1).collectLatest { list ->
                    _uiState.update { it.copy(studyTaskList = list) }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    data class HomeUiState(
        val selectedDate: LocalDate,
        val studyTaskList: ArrayList<Study> = arrayListOf(),
    ) {
        private val pattern = "MM월 dd일(E)"
        val selectedDateStr = selectedDate.dateFormat(pattern, Locale.KOREA)
    }
}
