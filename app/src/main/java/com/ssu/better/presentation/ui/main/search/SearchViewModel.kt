package com.ssu.better.presentation.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.better.data.datasources.UserPrefManager
import com.ssu.better.data.util.HttpException
import com.ssu.better.domain.usecase.study.GetStudyListByCategoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListByQueryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListUseCase
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.SortOption
import com.ssu.better.entity.study.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStudyListUseCase: GetStudyListUseCase,
    private val getStudyListByCategoryUseCase: GetStudyListByCategoryUseCase,
    private val getStudyListByQueryUseCase: GetStudyListByQueryUseCase,
    private val userPrefManager: UserPrefManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState get() = _uiState

    private val _userInfo = MutableStateFlow<String>("")
    val userInfo get() = _userInfo

    init {
        loadUserInfo()
        loadList()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            userPrefManager.getUserPref().collectLatest {
                it?.let { pref -> _userInfo.emit(pref.nickname) }
            }
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            _uiState.emit(SearchUiState.Loading)
            getStudyListUseCase.getStudyList().catch {
                Timber.e(it)
                when ((it as HttpException).code) {
                    404 -> {
                        _uiState.emit(SearchUiState.Fail("해당 카테고리는 존재하지 않습니다."))
                    }

                    else -> {
                        _uiState.emit(SearchUiState.Fail("오류가 발생했습니다."))
                    }
                }
            }.collectLatest { res ->
                if (res.isEmpty()) {
                    _uiState.emit(SearchUiState.Empty)
                } else {
                    _uiState.emit(SearchUiState.Success(res.sortedByDescending { it.groupRank.score }))
                }
            }
        }
    }

    fun loadListWithCategory(category: Category) {
        viewModelScope.launch {
            _uiState.emit(SearchUiState.Loading)
            getStudyListByCategoryUseCase.getStudyListByCategory(category.id).catch {
                Timber.e(it)
                when ((it as HttpException).code) {
                    404 -> {
                        _uiState.emit(SearchUiState.Fail("해당 카테고리는 존재하지 않습니다."))
                    }

                    else -> {
                        _uiState.emit(SearchUiState.Fail("오류가 발생했습니다."))
                    }
                }
            }.collectLatest { res ->
                if (res.isEmpty()) {
                    _uiState.emit(SearchUiState.Empty)
                } else {
                    _uiState.emit(SearchUiState.Success(res.sortedByDescending { it.groupRank.score }))
                }
            }
        }
    }

    fun loadListWithQuery(keyword: String, category: Category = Category.ALL) {
        val categoryId: Int? = if (category == Category.ALL) null else category.id.toInt()
        viewModelScope.launch {
            _uiState.emit(SearchUiState.Loading)
            getStudyListByQueryUseCase.getStudyListByQuery(keyword, categoryId).catch {
                Timber.e(it)
                when ((it as HttpException).code) {
                    404 -> {
                        _uiState.emit(SearchUiState.Fail("해당 카테고리는 존재하지 않습니다."))
                    }

                    else -> {
                        _uiState.emit(SearchUiState.Fail("오류가 발생했습니다."))
                    }
                }
            }.collectLatest { res ->
                if (res.isEmpty()) {
                    _uiState.emit(SearchUiState.Empty)
                } else {
                    _uiState.emit(SearchUiState.Success(res.sortedByDescending { it.groupRank.score }))
                }
            }
        }
    }

    fun sort(option: SortOption) {
        viewModelScope.launch {
            if (uiState.value == SearchUiState.Loading || uiState.value == SearchUiState.Empty) return@launch

            val origin = ArrayList<Study>((uiState.value as SearchUiState.Success).list)

            if (option == SortOption.LATEST) {
                _uiState.value = (SearchUiState.Success(origin.sortedByDescending { it.createdAt }))
            } else {
                _uiState.value = (SearchUiState.Success(origin.sortedByDescending { it.groupRank.score }))
            }
        }
    }

    sealed class SearchUiState {
        object Loading : SearchUiState()
        data class Success(val list: List<Study>) : SearchUiState()

        object Empty : SearchUiState()

        data class Fail(val message: String) : SearchUiState()
    }
}
