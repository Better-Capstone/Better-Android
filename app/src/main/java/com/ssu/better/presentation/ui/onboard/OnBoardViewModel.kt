package com.ssu.better.presentation.ui.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<OnBoardUiState>(OnBoardUiState("사용자", "사용자", false)) // TODO User Entity
    val uiState get() = _uiState.asStateFlow()

    private val _nickNameState = MutableStateFlow("")
    val nickNameState get() = _nickNameState.asStateFlow()

    init {
        validNickname()
    }

    private fun validNickname() {
        viewModelScope.launch {
            nickNameState.debounce(1000).collectLatest { query ->
                // API Call
                _uiState.update { it.copy(isValidNickName = nickNameState.value.length % 2 == 0) }
            }
        }
    }

    fun inputNickname(value: String) {
        _uiState.update { it.copy(nickname = value) }
    }

    data class OnBoardUiState(
        val nickname: String,
        val name: String,
        val isValidNickName: Boolean,
    )
}
