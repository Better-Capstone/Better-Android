package com.ssu.better.presentation.ui.challenge.approve

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.challenge.GetChallengeUseCase
import com.ssu.better.domain.usecase.challenge.PostChallengeCommentUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeVerifyRequest
import com.ssu.better.entity.study.Study
import com.ssu.better.presentation.state.IdleEvent
import com.ssu.better.util.getHttpErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChallengeApproveViewModel @Inject constructor(
    application: Application,
    private val getStudyUseCase: GetStudyUseCase,
    private val getChallengeUseCase: GetChallengeUseCase,
    private val postChallengeComment: PostChallengeCommentUseCase,
    private val userPrefManager: UserPrefManager,
) : AndroidViewModel(application) {
    sealed class ChallengeApproveEvent {
        object Load : ChallengeApproveEvent()
        data class Success(val challenge: Challenge, val study: Study, val isChecked: Boolean) : ChallengeApproveEvent()
    }

    private val _event: MutableStateFlow<ChallengeApproveEvent> = MutableStateFlow(ChallengeApproveEvent.Load)
    val event: StateFlow<ChallengeApproveEvent>
        get() = _event

    private var challengeId = 0L

    private val _idleEvent: MutableStateFlow<IdleEvent> = MutableStateFlow(IdleEvent.Idle)
    val idleEvent: StateFlow<IdleEvent>
        get() = _idleEvent

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent get() = _toastEvent

    fun load(studyId: Long, challengeId: Long) {
        this.challengeId = challengeId
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest { pref ->
                pref?.let {
                    getChallengeUseCase.getChallenge(challengeId)
                        .combine(getStudyUseCase.getStudy(studyId)) { challenge, study ->
                            ChallengeApproveEvent.Success(
                                challenge,
                                study,
                                challenge.approveMember.contains(pref.id) || challenge.rejectMember
                                    .contains(
                                        pref.id,
                                    ),
                            )
                        }.catch { t ->
                            if (t is HttpException) {
                                Timber.e(t.getHttpErrorMsg())
                            } else {
                                Timber.i(t.toString())
                            }
                        }
                        .collect {
                            _event.emit(it)
                        }
                }
            }
        }
    }

    fun onClickApprove() {
        viewModelScope.launch {
            postChallengeComment.postChallengeComment(challengeId, ChallengeVerifyRequest(true))
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.getHttpErrorMsg())
                        _toastEvent.emit(t.getHttpErrorMsg())
                    }
                }
                .collectLatest {
                    Timber.d("성공")
                    _idleEvent.emit(IdleEvent.Finish)
                }
        }
    }

    fun onClickReject() {
        viewModelScope.launch {
            postChallengeComment.postChallengeComment(challengeId, ChallengeVerifyRequest(false))
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.getHttpErrorMsg())
                        _toastEvent.emit(t.getHttpErrorMsg())
                    }
                }
                .collectLatest {
                    Timber.d("성공")
                    _idleEvent.emit(IdleEvent.Finish)
                }
        }
    }
}
