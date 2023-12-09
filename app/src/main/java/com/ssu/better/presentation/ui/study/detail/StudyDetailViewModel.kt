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
import com.ssu.better.entity.user.UserPref
import com.ssu.better.util.convertToLocalDateByFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.Period
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
        data class Success(val study: Study, val taskList: ArrayList<StudyTask>, val userPref: UserPref) : StudyEvent()
        object Load : StudyEvent()
        data class Fail(val message: String) : StudyEvent()
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
            combine(
                userManager.getUserPref(),
                getStudyUseCase.getStudy(studyId = studyId.toLong()),
                getStudyTaskListUseCase.getStudyTaskList(
                    studyId = studyId
                        .toLong(),
                ),
                ::Triple,
            ).catch { t ->
                if (t is HttpException) {
                    Timber.e(t.message)
                    when (t.code) {
                        401 -> Timber.e("401")
                        403 -> Timber.e("403 Forbidden")
                    }
                }
            }.collectLatest { res ->
                if (res.first == null) {
                    _studyEventStateFlow.emit(StudyEvent.Fail("유저 정보 실패"))
                    return@collectLatest
                }
                val list = res.third.toList().filter { it.user.userId == res.first?.id }.sortedByDescending { it.createdAt }
                _myTask.emit(if (list.isEmpty()) null else list[0])
                _studyEventStateFlow.emit(StudyEvent.Success(study = res.second, taskList = res.third, userPref = res.first!!))
            }
        }
    }

    fun isValidToAddTask(study: Study, task: StudyTask?): Boolean {
        val lastTask = study.taskGroupList.sortedByDescending { it.startDate }.firstOrNull()

        Timber.d("lastTask $lastTask")
        Timber.d("myTask $task")

        if (lastTask == null || task == null) {
            return true
        } else {
            val start = convertToLocalDateByFormat(lastTask.startDate, "yyyy-MM-dd") ?: return false
            val end = convertToLocalDateByFormat(lastTask.endDate, "yyyy-MM-dd") ?: return false
            val lastMyTaskStartDay = convertToLocalDateByFormat(task.createdAt, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

            if (task.taskGroup.taskGroupId == lastTask.taskGroupId) return false
            if (Period.between(LocalDate.now(), end).isNegative) return false
            if (Period.between(lastMyTaskStartDay, start).isNegative) return false
        }
        return true
    }
}
