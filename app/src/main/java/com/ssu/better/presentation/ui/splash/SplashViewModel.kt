package com.ssu.better.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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
                tokenManager.getAccessToken().catch { cause ->
                    Timber.e(cause)
                }.collectLatest { token ->
                    _authToken.emit(token)
                    Timber.d("token : $token")
                }
            }
        }
    }
}
