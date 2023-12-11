package com.ssu.better.presentation.ui.task.crate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.task.PostTaskUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.toDayOfWeek
import com.ssu.better.entity.task.TaskCreateRequest
import com.ssu.better.util.convertToLocalDateByFormat
import com.ssu.better.util.getDaysOfWeek
import com.ssu.better.util.getHttpErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
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
                _event.emit(CreateTaskEvent.Fail((it as HttpException).getHttpErrorMsg()))
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
                _event.emit(CreateTaskEvent.Fail((it as HttpException).getHttpErrorMsg()))
            }.collectLatest {
                _event.emit(CreateTaskEvent.Complete)
            }
        }
    }

    fun getNextCheckDay(study: Study): LocalDate {
        val lastTaskDate = study.taskGroupList?.sortedBy { it.endDate }?.lastOrNull()

        return when (study.period) {
            StudyPeriod.EVERYDAY -> {
                LocalDate.now().plusDays(1L)
            }

            StudyPeriod.WEEKLY -> {
                if (lastTaskDate != null) {
                    val end = convertToLocalDateByFormat(lastTaskDate.endDate, "yyyy-MM-dd") ?: LocalDate.now()
                    if (Period.between(LocalDate.now(), end).isNegative) {
                        convertToLocalDateByFormat(lastTaskDate.endDate, "yyyy-MM-dd")?.plusWeeks(1)
                    } else {
                        end
                    }
                } else {
                    // 첫 테스크인 경우
                    getDaysOfWeek(LocalDate.now()).first { it.dayOfWeek == study.checkDay.toDayOfWeek() }
                }
            }

            StudyPeriod.BIWEEKLY -> {
                if (lastTaskDate != null) {
                    val end = convertToLocalDateByFormat(lastTaskDate.endDate, "yyyy-MM-dd") ?: LocalDate.now()
                    if (Period.between(LocalDate.now(), end).isNegative) {
                        convertToLocalDateByFormat(lastTaskDate.endDate, "yyyy-MM-dd")?.plusWeeks(2)
                    } else {
                        end
                    }
                } else {
                    // 첫 테스크인 경우
                    getDaysOfWeek(LocalDate.now()).first { it.dayOfWeek == study.checkDay.toDayOfWeek() }
                }
            }
        } ?: LocalDate.now()
    }

    sealed class CreateTaskEvent {
        object Success : CreateTaskEvent()
        data class Fail(val message: String) : CreateTaskEvent()
        object Loading : CreateTaskEvent()
        object Complete : CreateTaskEvent()
    }

    data class CreateTaskUiState(val study: Study? = null, val content: String = "")
}
