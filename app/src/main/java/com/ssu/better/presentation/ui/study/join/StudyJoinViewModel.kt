package com.ssu.better.presentation.ui.study.join

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.study.GetStudyUserListUseCase
import com.ssu.better.domain.usecase.study.PostJoinStudyUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.entity.user.UserPref
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
class StudyJoinViewModel @Inject constructor(
    application: Application,
    private val getStudyUseCase: GetStudyUseCase,
    private val getStudyUserListUseCase: GetStudyUserListUseCase,
    private val postJoinStudyUseCase: PostJoinStudyUseCase,
    private val userPrefManager: UserPrefManager,
) : AndroidViewModel(application) {
    private var studyId: Int = 0

    sealed class UIState {
        object Loading : UIState()
        data class Success(val study: Study, val userList: ArrayList<StudyUser>, val userPref: UserPref) : UIState()

        object Finish : UIState()
    }

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState>
        get() = _uiState

    private val _hour: MutableStateFlow<String> = MutableStateFlow("")
    val hour: StateFlow<String>
        get() = _hour

    private val _minute: MutableStateFlow<String> = MutableStateFlow("")
    val minute: StateFlow<String>
        get() = _minute

    private val _isAm: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAm: StateFlow<Boolean>
        get() = _isAm

    fun setStudyId(studyId: Int) {
        this.studyId = studyId
        load()
    }

    private fun load() {
        viewModelScope.launch {
            combine(
                userPrefManager.getUserPref(),
                getStudyUseCase.getStudy(studyId.toLong()),
                getStudyUserListUseCase.getStudyUserList
                (studyId.toLong()),
                ::Triple,
            )
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.message)
                        when (t.code) {
                            403 -> Timber.e("403 Forbidden")
                            401 -> Timber.e("401")
                        }
                    } else {
                    }
                }
                .collectLatest {
                    if (it.first != null) _uiState.emit(UIState.Success(study = it.second, userPref = it.first!!, userList = it.third))
                }
        }
    }

    fun updateHour(hour: String) {
        viewModelScope.launch {
            _hour.emit(hour)
        }
    }

    fun updateMinute(minute: String) {
        viewModelScope.launch {
            _minute.emit(minute)
        }
    }

    fun updateIsAm(isAm: Boolean) {
        viewModelScope.launch {
            _isAm.emit(isAm)
        }
    }

    fun join() {
        viewModelScope.launch {
            postJoinStudyUseCase.joinStudy(studyId.toLong())
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.message)
                        when (t.code) {
                            403 -> Timber.e("403 Forbidden")
                            401 -> Timber.e("401")
                        }
                    }
                }
                .collectLatest {
                    Timber.d("스터디 가입 성공")
                    _uiState.emit(UIState.Finish)
                }
        }
    }
}
