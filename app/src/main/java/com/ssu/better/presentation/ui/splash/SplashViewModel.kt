package com.ssu.better.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {
    private val _authToken = MutableStateFlow<String?>("")
    val authToken get() = _authToken.asStateFlow()

    init {
        getAuthToken()
    }

    private fun getAuthToken() {
        viewModelScope.launch {
            run {
                tokenManager.getAccessToken().collect { token ->
                    _authToken.update { token }
                }
            }
        }
    }
}
