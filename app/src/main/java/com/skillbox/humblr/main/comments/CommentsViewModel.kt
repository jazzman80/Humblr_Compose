package com.skillbox.humblr.main.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import kotlinx.coroutines.launch


class CommentsViewModel(
    private val repository: Repository
) : ViewModel() {

    val comments: LiveData<List<CommentDto>>
        get() = _comments
    private val _comments = MutableLiveData<List<CommentDto>>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState
    private val _loadingState = MutableLiveData(LoadingState.LOADING)

    suspend fun getComments(article: String) {

        _loadingState.value = LoadingState.LOADING

        refreshToken()

        val result = repository.getComments(article)

        if (result.isSuccessful) {
            val things = result.body()?.get(1)?.data?.children

            val newComments = mutableListOf<CommentDto>()
            val commentsWithAvatars = mutableListOf<CommentDto>()

            if (things != null) {
                for (thing in things) {
                    newComments.add(thing.data.toCommentDto())
                }
            }

            var preloadedAvatars = 0

            if (newComments.size < 5) {
                preloadedAvatars = newComments.size
            } else {
                preloadedAvatars = 5
            }


            repeat(preloadedAvatars) {

                val comment = newComments[it]

                val avatarResult = repository.getUser(comment.author ?: "")

                if (avatarResult.isSuccessful) {
                    val avatar = avatarResult.body()?.data?.toAccountDto()?.iconImg
                    newComments[it] = comment.copy(avatar = avatar)
                }
            }

            _comments.value = newComments

            repeat(newComments.size - preloadedAvatars) {

                val comment = newComments[it + preloadedAvatars]

                val avatarResult = repository.getUser(comment.author ?: "")

                if (avatarResult.isSuccessful) {
                    val avatar = avatarResult.body()?.data?.toAccountDto()?.iconImg
                    newComments[it + preloadedAvatars] = comment.copy(avatar = avatar)
                    _comments.value = newComments
                }
            }

            _loadingState.value = LoadingState.SUCCESS
        } else {
            _loadingState.value = LoadingState.ERROR
        }
    }


    private fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

    suspend fun download(comment: CommentDto) {
        repository.download(comment)
    }

}