package com.ssu.better.presentation.ui.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.TokenManager
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.domain.usecase.user.PostUserRegisterUseCase
import com.ssu.better.entity.user.UserRegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val registerUseCase: PostUserRegisterUseCase,
    private val tokenManager: TokenManager,
    private val userPrefManager: UserPrefManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnBoardUiState>(OnBoardUiState("", false)) // TODO User Entity
    val uiState get() = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<OnBoardEvent>()
    val events get() = _events.asSharedFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading

    fun inputNickname(value: String) {
        Timber.d("name : $value")
        _uiState.update { it.copy(nickname = value) }
    }

    fun saveToken(token: String) {
        Timber.d("name : $token")
        _uiState.update { it.copy(token = token) }
    }

    fun postRegisterUser() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                with(uiState.value) {
                    registerUseCase.registerUser(UserRegisterRequest(token, nickname)).collectLatest {
                        tokenManager.saveAccessToken(token)
                        userPrefManager.updateUserId(it.id)
                        userPrefManager.updateNickName(it.nickname)
                    }
                }
                _isLoading.value = false
                _events.emit(OnBoardEvent.NavToMain)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    data class OnBoardUiState(
        val nickname: String,
        val isValidNickName: Boolean,
        val token: String = "",
    )

    sealed class OnBoardEvent {
        object NavToMain : OnBoardEvent()
    }
}
