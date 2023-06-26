package com.skillbox.humblr.main.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skillbox.humblr.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    val postFlow = repository.getPopularSubs().flow.cachedIn(viewModelScope)

    init {
        refreshToken()
    }

    fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

}