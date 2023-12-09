package com.ssu.better.presentation.ui.study.detail.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.user.GetUserTasksUseCase
import com.ssu.better.entity.task.UserTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyMyTasksViewModel @Inject constructor(
    private val userPrefManager: UserPrefManager,
    private val getTasksByUserUseCase: GetUserTasksUseCase,
) : ViewModel() {

    private val _uistate = MutableStateFlow<MyTasksUiState>(MyTasksUiState.Loading)
    val uistate get() = _uistate

    fun getStudyTasks(studyId: Long) {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                it?.let {
                    getTasksByUserUseCase.getUserTasks(it.id).collectLatest {
                        val tasks = it.filter { it.study.id == studyId }
                        if (tasks.isEmpty()) {
                            _uistate.emit(MyTasksUiState.Empty)
                        } else {
                            _uistate.emit(MyTasksUiState.Success(tasks))
                        }
                    }
                }
            }
        }
    }

    sealed class MyTasksUiState {
        object Loading : MyTasksUiState()
        data class Success(val tasks: List<UserTask>) : MyTasksUiState()
        object Empty : MyTasksUiState()
    }
}
