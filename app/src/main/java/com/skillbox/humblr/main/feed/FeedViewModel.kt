package com.skillbox.humblr.main.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skillbox.humblr.core.Repository
import kotlinx.coroutines.launch

class FeedViewModel(
    val repository: Repository
) : ViewModel() {

    val newSubsFlow = repository.getNewSubs().flow.cachedIn(viewModelScope)
    val popularSubsFlow = repository.getPopularSubs().flow.cachedIn(viewModelScope)

    init {
        refreshToken()
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