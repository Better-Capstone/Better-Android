package com.ssu.better.presentation.ui.main.user_rank_history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.user.GetUserRankHistoryUseCase
import com.ssu.better.entity.user.UserRankHistory
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
class UserRankHistoryViewModel @Inject constructor(
    application: Application,
    private val getUserRankHistoryUseCase: GetUserRankHistoryUseCase,
    private val userPrefManager: UserPrefManager,
) : AndroidViewModel(application) {
    sealed class UserRankEvent {
        object Load : UserRankEvent()
        data class Success(val userRankHistoryList: List<UserRankHistory>) : UserRankEvent()
    }

    private val _event: MutableStateFlow<UserRankEvent> = MutableStateFlow(UserRankEvent.Load)
    val event: StateFlow<UserRankEvent>
        get() = _event

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest { user ->
                getUserRankHistoryUseCase.getUserRankHistory(user?.id ?: -1)
                    .catch { t ->
                        if (t is HttpException) {
                            Timber.e(t.getHttpErrorMsg())
                        }
                    }
                    .collectLatest {
                        _event.emit(UserRankEvent.Success(it))
                    }
            }
        }
    }
}
