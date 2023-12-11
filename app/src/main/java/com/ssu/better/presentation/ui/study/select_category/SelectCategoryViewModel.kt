package com.ssu.better.presentation.ui.study.select_category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.entity.study.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    val categoryList = Category.values().toList().subList(0, 6)

    sealed class Event {
        data class CategoryClick(val category: Category) : Event()
        object Finish : Event()
    }

    private val _eventSharedFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    val eventSharedFlow: SharedFlow<Event>
        get() = _eventSharedFlow

    fun onClickCategory(category: Category) {
        viewModelScope.launch {
            _eventSharedFlow.emit(Event.CategoryClick(category))
        }
    }

    fun onClickFinish() {
        viewModelScope.launch {
            _eventSharedFlow.emit(Event.Finish)
        }
    }
}
