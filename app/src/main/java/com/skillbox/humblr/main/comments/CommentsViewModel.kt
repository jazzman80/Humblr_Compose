package com.skillbox.humblr.main.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.Thing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getCommentsFlow(article: String): Flow<PagingData<Thing>> {
        return repository.getComments(article).flow.cachedIn(viewModelScope)
    }

    init {
        refreshToken()
    }

    private fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

}