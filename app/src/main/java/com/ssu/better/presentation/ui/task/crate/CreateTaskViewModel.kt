package com.ssu.better.presentation.ui.task.crate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.task.PostTaskUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.TaskCreateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: PostTaskUseCase,
    private val getStudyUseCase: GetStudyUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<CreateTaskEvent>()
    val event get() = _event

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState get() = _uiState

    fun getStudy(studyId: Long) {
        viewModelScope.launch {
            getStudyUseCase.getStudy(studyId).catch {
                _event.emit(CreateTaskEvent.Fail("스터디 조회 실패"))
            }.collectLatest { study ->
                _event.emit(CreateTaskEvent.Success)
                _uiState.emit(CreateTaskUiState(study, ""))
            }
        }
    }

    fun onChangeContent(value: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(content = value) }
        }
    }

    fun onClickComplete(studyId: Long) {
        viewModelScope.launch {
            _event.emit(CreateTaskEvent.Loading)
            createTaskUseCase.createTask(TaskCreateRequest(studyId, uiState.value.content)).catch {
                _event.emit(CreateTaskEvent.Fail("태스크 등록 실패"))
            }.collectLatest {
                _event.emit(CreateTaskEvent.Complete)
            }
        }
    }

    sealed class CreateTaskEvent {
        object Success : CreateTaskEvent()
        data class Fail(val message: String) : CreateTaskEvent()
        object Loading : CreateTaskEvent()
        object Complete : CreateTaskEvent()
    }

    data class CreateTaskUiState(val study: Study? = null, val content: String = "")
}
