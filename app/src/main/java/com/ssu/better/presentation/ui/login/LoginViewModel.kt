package com.ssu.better.presentation.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssu.better.data.util.TokenManager
import com.ssu.better.domain.usecase.PostUserLoginRequestUseCase
import com.ssu.better.domain.usecase.PostUserRegisterUseCase
import com.ssu.better.entity.user.UserLoginRequest
import com.ssu.better.entity.user.UserRegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUseCase: PostUserRegisterUseCase,
    private val loginRequestUseCase: PostUserLoginRequestUseCase,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _loginEvents = MutableSharedFlow<LoginEvent>()
    val loginEvents get() = _loginEvents.asSharedFlow()

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e("카카오톡으로 로그인 실패", error)
        } else if (token != null) {
            Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
            requestUserInfo(token)
        }
    }

    fun kakaoLogin() {
        viewModelScope.launch {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Timber.e("카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                        requestUserInfo(token)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    private fun requestUserInfo(token: OAuthToken) {
        viewModelScope.launch {
            UserApiClient.instance.accessTokenInfo { _, _ ->
                UserApiClient.instance.me { user, _ ->
                    val nickname = user?.kakaoAccount?.profile?.nickname ?: ""
                    val id = user?.id ?: -1
                    Timber.d("kakao_nick", nickname)
                    Timber.d("kakao_id", id.toString())

                    postUserInfo(token, nickname)
                }
            }
        }
    }

    private fun postUserInfo(token: OAuthToken, nickname: String = "") {
        viewModelScope.launch {
            _loginEvents.emit(LoginEvent.NavToOnBoard)
            try {
                loginRequestUseCase.login(UserLoginRequest(token.accessToken)).collectLatest {
                    tokenManager.saveAccessToken(it.accessToken)
                    _loginEvents.emit(LoginEvent.NavToMain)
                }.also {
                    /*신규 회원*/
                    registerUseCase.registerUser(UserRegisterRequest(token.accessToken, nickname)).collectLatest {
                        _loginEvents.emit(LoginEvent.NavToOnBoard)
                        // TODO 신규 or 기존 회원 분기 처리
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
                _loginEvents.emit(LoginEvent.LoginFail)
            }
        }
    }

    sealed class LoginEvent {
        object LoginFail : LoginEvent()
        object NavToMain : LoginEvent()
        object NavToOnBoard : LoginEvent()
    }
}
