package com.ssu.better.presentation.ui.study.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyTaskListUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.StudyTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StudyDetailViewModel @Inject constructor(
    application: Application,
    private val getStudyUseCase: GetStudyUseCase,
    private val getStudyTaskListUseCase: GetStudyTaskListUseCase,
    private val userManager: UserPrefManager,
) : AndroidViewModel(application) {
    private var studyId: Int = 0

    sealed class StudyEvent {
        data class Success(val study: Study, val taskList: ArrayList<StudyTask>) : StudyEvent()
        object Load : StudyEvent()
    }

    private val _myTask: MutableStateFlow<StudyTask?> = MutableStateFlow(null)
    val myTask: StateFlow<StudyTask?>
        get() = _myTask

    fun setStudyId(studyId: Int) {
        this.studyId = studyId
        load()
    }

    private val _studyEventStateFlow: MutableStateFlow<StudyEvent> = MutableStateFlow(StudyEvent.Load)
    val studyEventStateFlow: StateFlow<StudyEvent>
        get() = _studyEventStateFlow

    private fun load() {
        viewModelScope.launch {
            getStudyUseCase.getStudy(studyId = studyId.toLong())
                .combine(getStudyTaskListUseCase.getStudyTaskList(studyId = studyId.toLong())) { study: Study, taskList: ArrayList<StudyTask> ->

                    return@combine StudyEvent.Success(study, taskList)
                }
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.message)
                        when (t.code) {
                            401 -> Timber.e("401")
                            403 -> Timber.e("403 Forbidden")
                        }
                    }
                }
                .collectLatest { event ->
                    val user = userManager.getUserPref().first()
                    val list = event.taskList.filter { it.user.userId == user?.id }
                    _myTask.emit(if (list.isEmpty()) null else list[0])
                    _studyEventStateFlow.emit(event)
                }
        }
    }
}
