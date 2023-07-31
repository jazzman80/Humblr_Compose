package com.skillbox.humblr.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.Subreddit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository
) : ViewModel() {

    init {
        refreshToken()
    }

    fun getSubs(searchQuery: String): Flow<PagingData<Subreddit>> {
        return repository.searchSubs(searchQuery).flow
    }

    fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

    fun subscribe(isSubscribed: Boolean, name: String) {
        viewModelScope.launch {
            if (isSubscribed) {
                repository.unsubscribe(name)
            } else {
                repository.subscribe(name)
            }
        }
    }
}