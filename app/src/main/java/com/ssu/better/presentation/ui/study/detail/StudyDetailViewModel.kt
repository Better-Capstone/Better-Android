package com.ssu.better.presentation.ui.study.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ssu.better.entity.study.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StudyDetailViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    sealed class StudyEvent {
        data class Success(val study: Study) : StudyEvent()
        object Load : StudyEvent()
    }

    private val _studyEventStateFlow: MutableStateFlow<StudyEvent> = MutableStateFlow(StudyEvent.Load)
    val studyEventStateFlow: StateFlow<StudyEvent>
        get() = _studyEventStateFlow
}
