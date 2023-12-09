package com.ssu.better.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.study.GetStudyListByUserUseCase
import com.ssu.better.domain.usecase.user.GetUserTasksUseCase
import com.ssu.better.entity.task.UserTask
import com.ssu.better.entity.task.UserTaskStudy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserTasksUseCase: GetUserTasksUseCase,
    private val getUserStudyCase: GetStudyListByUserUseCase,
    private val userPrefManager: UserPrefManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState get() = _uiState

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate get() = _selectedDate

    fun changeSelectedDate(date: LocalDate) {
        _selectedDate.update { date }
    }

    fun loadStudyTaskList() {
        viewModelScope.launch {
            _uiState.emit(HomeUiState.Loading)

            userPrefManager.getUserPref().catch {
                _uiState.emit(HomeUiState.Fail("사용자 정보 로드 실패"))
            }.collectLatest { it ->
                if (it?.id == null) {
                    _uiState.emit(HomeUiState.Fail("사용자 정보 로드 실패"))
                    return@collectLatest
                }
                val userId = it.id
                getUserTasksUseCase.getUserTasks(userId).combine(
                    getUserStudyCase.getStudyListByUser(userId),
                ) { taskList, studyList ->
                    val validStudyIds = studyList.map { it.studyId }
                    val validTasks = taskList.filter { validStudyIds.contains(it.study.id) }.groupBy { UserTaskStudy(it.study.id, it.study.title) }
                    validTasks
                }.catch {
                    _uiState.emit(HomeUiState.Fail("사용자 정보 로드 실패"))
                }.collectLatest { validTasks ->
                    if (validTasks.isEmpty()) {
                        _uiState.emit(HomeUiState.Empty)
                    } else {
                        _uiState.emit(HomeUiState.Success(selectedDate.value, validTasks))
                    }
                }
            }
        }
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val selectedDate: LocalDate, val list: Map<UserTaskStudy, List<UserTask>>) : HomeUiState()

        data class Fail(val message: String) : HomeUiState()

        object Empty : HomeUiState()
    }
}
