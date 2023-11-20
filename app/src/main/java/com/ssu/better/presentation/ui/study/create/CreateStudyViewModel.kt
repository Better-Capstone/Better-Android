package com.ssu.better.presentation.ui.study.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.PostCreateStudyUseCase
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.study.StudyRequest
import com.ssu.better.entity.user.UserRankName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateStudyViewModel @Inject constructor(
    application: Application,
    private val postCreateStudyUseCase: PostCreateStudyUseCase,
) : AndroidViewModel(application) {
    private val _kickCondition = MutableStateFlow("")
    private var categoryId: Int = 0

    sealed class Event {
        object Idle : Event()
        object Finish : Event()
    }

    private val _event: MutableStateFlow<Event> = MutableStateFlow(Event.Idle)
    val event: StateFlow<Event>
        get() = _event

    val kickCondition: StateFlow<String>
        get() = _kickCondition

    private val _title = MutableStateFlow("")
    val title: StateFlow<String>
        get() = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    private val _checkDay = MutableStateFlow(StudyCheckDay.MON)
    val checkDay: StateFlow<StudyCheckDay>
        get() = _checkDay

    private val _period = MutableStateFlow(StudyPeriod.EVERYDAY)
    val period: StateFlow<StudyPeriod>
        get() = _period

    private val _minRank = MutableStateFlow(UserRankName.CANDLE)
    val minRank: StateFlow<UserRankName>
        get() = _minRank

    private val _buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean>
        get() = _buttonEnabled

    fun setCategoryId(categoryId: Int) {
        this.categoryId = categoryId
    }

    fun updateKickCondition(condition: String) {
        viewModelScope.launch {
            _kickCondition.emit(condition)
            checkValidation()
        }
    }

    fun updateTitle(title: String) {
        viewModelScope.launch {
            _title.emit(title)
            checkValidation()
        }
    }

    fun updateDescription(description: String) {
        viewModelScope.launch {
            _description.emit(description)
            checkValidation()
        }
    }

    fun updatePeriod(period: StudyPeriod) {
        viewModelScope.launch {
            _period.emit(period)
        }
    }

    fun updateCheckDay(checkDay: StudyCheckDay) {
        viewModelScope.launch {
            _checkDay.emit(checkDay)
        }
    }

    fun updateMinRank(minRank: UserRankName) {
        viewModelScope.launch {
            _minRank.emit(minRank)
        }
    }

    private fun checkValidation() {
        viewModelScope.launch {
            _buttonEnabled.emit(
                _kickCondition.value.isNotEmpty() && _kickCondition.value.toIntOrNull() != null &&
                    _title.value.isNotEmpty() &&
                    _description.value.isNotEmpty(),
            )
        }
    }

    fun createStudy() {
        viewModelScope.launch {
            val studyRequest = StudyRequest(
                categoryId = categoryId.toLong(),
                title = _title.value,
                description = _description.value,
                checkDay = _checkDay.value,
                kickCondition = _kickCondition.value.toInt(),
                maximumCount = 50, // TODO 입력창 디자인 받기
                minRank = _minRank.value.limit,
                period = _period.value,
            )
            postCreateStudyUseCase.createStudy(studyRequest)
                .catch { t ->
                    if (t is HttpException) {
                        Timber.e(t.message)
                        when (t.code) {
                            403 -> Timber.e("403 Forbidden")
                            401 -> Timber.e("401")
                        }
                    }
                }
                .collect {
                    Timber.d("스터디 생성 성공")
                    _event.emit(Event.Finish)
                }
        }
    }
}
