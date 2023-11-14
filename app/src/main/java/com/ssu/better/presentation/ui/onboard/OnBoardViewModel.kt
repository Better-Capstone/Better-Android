package com.ssu.better.presentation.ui.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<OnBoardUiState>(OnBoardUiState("", false)) // TODO User Entity
    val uiState get() = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<OnBoardEvent>()
    val events get() = _events.asSharedFlow()

    fun inputNickname(value: String) {
        _uiState.update { it.copy(nickname = value) }
    }

    fun postRegisterUser() {
        viewModelScope.launch {
            _events.emit(OnBoardEvent.NavToMain)
        }
    }

    data class OnBoardUiState(
        val nickname: String,
        val isValidNickName: Boolean,
    )

    sealed class OnBoardEvent {
        object NavToMain : OnBoardEvent()
    }
}
