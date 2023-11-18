package com.ssu.better.presentation.ui.study.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.entity.study.StudyCheckDay
import com.ssu.better.entity.study.StudyPeriod
import com.ssu.better.entity.user.UserRankName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStudyViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _kickCondition = MutableStateFlow("")
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

    fun updateKickCondition(condition: String) {
        viewModelScope.launch {
            _kickCondition.emit(condition)
        }
    }
    fun updateTitle(title: String) {
        viewModelScope.launch {
            _title.emit(title)
        }
    }

    fun updateDescription(description: String) {
        viewModelScope.launch {
            _description.emit(description)
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
}
