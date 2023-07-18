package com.skillbox.humblr.main.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    fun getCommentsFlow(article: String): Flow<PagingData<Thing>> {
//        return repository.getComments(article).flow.cachedIn(viewModelScope)
//    }

    val comments: LiveData<List<CommentDto>>
        get() = _comments
    private val _comments = MutableLiveData<List<CommentDto>>()

    init {
        refreshToken()
    }

    fun getComments(article: String) {
        viewModelScope.launch {
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

                for (comment in newComments) {

                    val avatarResult = repository.getUser(comment.author ?: "")

                    if (avatarResult.isSuccessful) {

                        val avatar = avatarResult.body()?.data?.toAccountDto()?.iconImg

                        val commentWithAvatar = comment.copy(avatar = avatar)

                        commentsWithAvatars.add(commentWithAvatar)

                    } else {
                        commentsWithAvatars.add(comment)
                    }
                }

                _comments.value = commentsWithAvatars
            }
        }
    }

    private fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

}