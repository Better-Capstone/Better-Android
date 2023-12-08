package com.ssu.better.presentation.ui.study.member_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyUserListUseCase
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.util.getHttpErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemberListViewModel @Inject constructor(
    application: Application,
    private val getStudyUserListUseCase: GetStudyUserListUseCase,
) : AndroidViewModel(application) {
    sealed class MemberListEvent {
        object Load : MemberListEvent()
        data class Success(val userList: ArrayList<StudyUser>) : MemberListEvent()
    }

    private val _event: MutableStateFlow<MemberListEvent> = MutableStateFlow(MemberListEvent.Load)
    val event: StateFlow<MemberListEvent>
        get() = _event

    fun load(studyId: Long) {
        viewModelScope.launch {
            getStudyUserListUseCase.getStudyUserList(studyId)
                .catch { t ->
                    if (t is HttpException) {
                        val error = t.getHttpErrorMsg()
                        Timber.e(error)
                    }
                }
                .collectLatest {
                    _event.emit(MemberListEvent.Success(it))
                }
        }
    }
}
