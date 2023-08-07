package com.skillbox.humblr.main.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skillbox.humblr.core.Repository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: Repository
) : ViewModel() {
    val subsFlow = repository.getFavoriteSubs().flow.cachedIn(viewModelScope)
    val favoriteComments = repository.getSavedComments().flow.cachedIn(viewModelScope)

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