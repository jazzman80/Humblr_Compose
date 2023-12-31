package com.skillbox.humblr.main.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: Repository
) : ViewModel() {

    fun postFlow(title: String): Flow<PagingData<Post>> {
        return repository.getPosts(title).flow.cachedIn(viewModelScope)
    }

    init {
        refreshToken()
    }

    fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

    fun save(isSaved: Boolean, name: String) {
        viewModelScope.launch {
            if (isSaved) {
                repository.unsave(name)
            } else {
                repository.save(name)
            }
        }
    }

}