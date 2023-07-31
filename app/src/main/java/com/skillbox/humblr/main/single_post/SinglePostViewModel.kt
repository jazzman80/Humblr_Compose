package com.skillbox.humblr.main.single_post

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.PostDto
import kotlinx.coroutines.launch

class SinglePostViewModel(
    private val repository: Repository
) : ViewModel() {

    val post: LiveData<PostDto>
        get() = _post
    private val _post = MutableLiveData<PostDto>()

    val comment: LiveData<CommentDto>
        get() = _comment
    private val _comment = MutableLiveData<CommentDto>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState
    private val _loadingState = MutableLiveData(LoadingState.LOADING)

//    val avatar: LiveData<String>
//        get() = _avatar
//    private val _avatar = MutableLiveData<String>()


    suspend fun getPostWithComment(article: String) {
//        viewModelScope.launch {

        _loadingState.value = LoadingState.LOADING

        refreshToken()

        val result = repository.getPostWithComment(article)

        if (result.isSuccessful) {

            result.body()?.first()?.data?.children?.first()?.data?.toPostDataDto()
                .let { newPost ->
                    _post.value = newPost

                    if (newPost!!.numComments != null && newPost.numComments!! > 0) {
                        result.body()?.get(1)?.data?.children?.first()?.data?.toCommentDto()
                            .let { comment ->
                                val avatar = repository.getUser(comment?.author ?: "")
                                    .body()?.data?.toAccountDto()?.iconImg

                                val newComment = comment!!.copy(avatar = avatar)
                                _comment.value = newComment
                            }
                    } else {
                        _comment.value = CommentDto()
                    }


                }
            _loadingState.value = LoadingState.SUCCESS
        } else {
            _loadingState.value = LoadingState.ERROR
        }
//        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken()
        }
    }

    fun save(isSubscribed: Boolean, name: String) {
        viewModelScope.launch {
            if (isSubscribed) {
                repository.unsave(name)
            } else {
                repository.save(name)
            }
        }
    }

    fun share(context: Context, permalink: String): Boolean {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://www.reddit.com$permalink")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(context, shareIntent, null)

        return false
    }

    fun download(comment: CommentDto) {
        viewModelScope.launch {
            repository.download(comment)
        }
    }

}