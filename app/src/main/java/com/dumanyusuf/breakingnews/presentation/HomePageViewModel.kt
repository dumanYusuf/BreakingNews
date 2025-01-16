package com.dumanyusuf.breakingnews.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.breakingnews.domain.use_case.get_breaking_news.GetBreakingNewsUseCase
import com.dumanyusuf.breakingnews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val getBreakingNewsUseCase: GetBreakingNewsUseCase) :ViewModel() {

    private val _state= MutableStateFlow<HomeState>(HomeState())
    val state:StateFlow<HomeState> = _state

    private val _isSearchVisible = mutableStateOf(false)
    val isSearchVisible: State<Boolean> = _isSearchVisible

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery



    fun loadNewsBreaking(country:String){
        getBreakingNewsUseCase.getBreakingNews(country).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                    Log.d("loading","Loading news data...")
                }
                is Resource.Success -> {
                    _state.value = HomeState(
                        newList = result.data ?: emptyList(),
                        isLoading = false
                    )
                    Log.d("Success", "Loaded ${result.data?.size ?: 0} articles")
                }
                is Resource.Error -> {
                    _state.value = HomeState(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                    Log.e("Error", "Failed to load news: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun onSearchVisibilityChange() {
        _isSearchVisible.value = !_isSearchVisible.value
        if (!_isSearchVisible.value) {
            _searchQuery.value = ""
            loadNewsBreaking("us")
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            loadNewsBreaking("us")
        } else {
            searchNews(query)
        }
    }

    private fun searchNews(query: String) {
        // TODO: Implement search functionality with the API
        // For now, just filter the existing news list
        getBreakingNewsUseCase.getBreakingNews("us").onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val filteredList = result.data?.filter {
                        it.title?.contains(query, ignoreCase = true) == true ||
                        it.description?.contains(query, ignoreCase = true) == true
                    } ?: emptyList()
                    _state.value = HomeState(newList = filteredList)
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = HomeState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}