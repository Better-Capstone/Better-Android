package com.ssu.better.presentation.ui.challenge.create

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.task.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(
    application: Application,
    private val getStudyUseCase: GetStudyUseCase,
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

    sealed class ChallengeCreateEvent() {
        object Load : ChallengeCreateEvent()
        object Finish : ChallengeCreateEvent()

        data class Success(val study: Study, val task: Task) : ChallengeCreateEvent()
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

    fun load(studyId: Int, taskId: Int) {
    }

    fun postChallenge() {
    }
}
