package com.ssu.better.presentation.ui.challenge.create

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.task.GetTaskUseCase
import com.ssu.better.domain.usecase.task.PostCreateChallengeUseCase
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.Task
import com.ssu.better.util.getHttpErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(
    application: Application,
    private val getStudyUseCase: GetStudyUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val postCreateChallengeUseCase: PostCreateChallengeUseCase,
) : AndroidViewModel(application) {
    private val _imageUri: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val imageUri: StateFlow<Uri?>
        get() = _imageUri

    private val _description: MutableStateFlow<String> = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    private val _event: MutableStateFlow<ChallengeCreateEvent> = MutableStateFlow(ChallengeCreateEvent.Load)
    val event: StateFlow<ChallengeCreateEvent>
        get() = _event

    private var study: Study? = null
    private var task: Task? = null

    sealed class ChallengeCreateEvent() {
        object Load : ChallengeCreateEvent()
        object Finish : ChallengeCreateEvent()

        data class Success(val study: Study, val task: Task) : ChallengeCreateEvent()
    }

    fun load(studyId: Long, taskId: Long) {
        viewModelScope.launch {
            getStudyUseCase.getStudy(studyId)
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.getHttpErrorMsg())
                    }
                }
                .combine(getTaskUseCase.getTask(taskId)) { study: Study, task: Task ->
                    this@ChallengeCreateViewModel.study = study
                    this@ChallengeCreateViewModel.task = task
                    ChallengeCreateEvent.Success(study, task)
                }
                .collectLatest {
                    _event.emit(it)
                }
        }
    }

    fun updateImageUri(uri: Uri?) {
        viewModelScope.launch {
            _imageUri.emit(uri)
        }
    }

    fun updateDescription(value: String) {
        viewModelScope.launch {
            _description.emit(value)
        }
    }

    fun onClickFinish() {
        viewModelScope.launch {
            _event.emit(ChallengeCreateEvent.Finish)
        }
    }

    fun postChallenge() {
        task?.let {
            val request = ChallengeRequest(
                title = it.title,
                description = _description.value,
                image = "https://velog.velcdn.com/images/spy03128/post/f9592660-5745-4317-bcc8-96cff69b368e/image.png",
            )

            viewModelScope.launch {
                postCreateChallengeUseCase.postCreateChallenge(it.taskId, request)
                    .collectLatest {
                        Timber.d("태스크 생성 성공")
                        _event.emit(ChallengeCreateEvent.Finish)
                    }
            }
        }
    }
}
