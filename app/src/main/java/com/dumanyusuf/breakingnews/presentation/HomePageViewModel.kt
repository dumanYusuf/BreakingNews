package com.dumanyusuf.breakingnews.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.breakingnews.domain.use_case.get_bbc_news.GetBbcNewsUseCase
import com.dumanyusuf.breakingnews.domain.use_case.get_breaking_news.GetBreakingNewsUseCase
import com.dumanyusuf.breakingnews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val getBbcNewsUseCase: GetBbcNewsUseCase
) :ViewModel() {

    private val _state= MutableStateFlow<HomeState>(HomeState())
    val state:StateFlow<HomeState> = _state

    private val _stateBbc= MutableStateFlow<HomeState>(HomeState())
    val stateBbc:StateFlow<HomeState> = _stateBbc

    private val _searchResults = MutableStateFlow<HomeState>(HomeState())
    val searchResults: StateFlow<HomeState> = _searchResults


    init {
        loadBbcNews()
    }


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

    fun loadBbcNews(){
        getBbcNewsUseCase.getBbcNews().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _stateBbc.value = HomeState(isLoading = true)
                    Log.d("loading","Loading news data...")
                }
                is Resource.Success -> {
                    _stateBbc.value = HomeState(
                        newList = result.data ?: emptyList(),
                        isLoading = false
                    )
                    Log.d("Success", "Loaded ${result.data?.size ?: 0} articles")
                }
                is Resource.Error -> {
                    _stateBbc.value = HomeState(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                    Log.e("Error", "Failed to load news: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun searchNews(query: String) {
        if (query.isEmpty()) {
            _searchResults.value = _stateBbc.value
            return
        }

        val filteredList = _stateBbc.value.newList.filter { article ->
            article.title?.contains(query, ignoreCase = true) == true ||
            article.description?.contains(query, ignoreCase = true) == true
        }

        _searchResults.value = HomeState(
            newList = filteredList,
            isLoading = false
        )
    }


}