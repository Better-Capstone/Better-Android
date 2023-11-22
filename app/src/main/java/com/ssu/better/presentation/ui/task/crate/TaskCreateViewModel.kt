package com.ssu.better.presentation.ui.task.crate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskCreateViewModel @Inject constructor() : ViewModel() {
    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate get() = _selectedDate

    private val _content = MutableStateFlow<String>("")
    val content get() = _content

    fun onChangeContent(value: String) {
        viewModelScope.launch {
            _content.emit(value)
        }
    }

    fun onChangeSelectedDate(date: LocalDate) {
        viewModelScope.launch {
            _selectedDate.emit(date)
        }
    }
}
