package com.ssu.better.presentation.ui.main.mypage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyListByUserUseCase
import com.ssu.better.domain.usecase.user.GetUserRankUseCase
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.user.UserRank
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
class MyPageViewModel @Inject constructor(
    application: Application,
    private val userPrefManager: UserPrefManager,
    private val getUserRankUseCase: GetUserRankUseCase,
    private val getStudyListByUSerUseCase: GetStudyListByUserUseCase,
) : AndroidViewModel(application) {
    sealed class UIState {
        object Loading : UIState()
        data class Success(val userRank: UserRank, val studyList: List<Study>) : UIState()
    }

    private val _isNotifyEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isNotifyEnabled: StateFlow<Boolean>
        get() = _isNotifyEnabled

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState>
        get() = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                val userId = it?.id ?: -1
                getUserRankUseCase.getUserRank(userId)
                    .combine(getStudyListByUSerUseCase.getStudyListByUser(userId)) {
                            userRank: UserRank, studyList: ArrayList<Study> ->
                        UIState.Success(userRank, studyList)
                    }
                    .catch { t ->
                        Timber.e("error : ${t.message}")
                        if (t is HttpException) {
                            when (t.code) {
                                403 -> {
                                    Timber.e("권한 오류")
                                }

                                401 -> {
                                    Timber.e("BAD REQUEST")
                                }
                            }
                        }
                    }
                    .collect { uiState ->
                        _uiState.emit(uiState)
                    }
            }
        }
    }

    fun changeNotify(isNotifyEnabled: Boolean) {
        viewModelScope.launch {
            _isNotifyEnabled.emit(isNotifyEnabled)
        }
    }
}
