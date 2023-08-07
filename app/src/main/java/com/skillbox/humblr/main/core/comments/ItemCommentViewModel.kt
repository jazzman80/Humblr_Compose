package com.skillbox.humblr.main.core.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto

class ItemCommentViewModel(
    private val repository: Repository
) : ViewModel() {

    val avatar: LiveData<String?>
        get() = _avatar
    private val _avatar = MutableLiveData<String?>()

    suspend fun like(liked: Boolean, name: String) {
        if (liked) {
            repository.unsave(name)
        } else {
            repository.save(name)
        }
    }

    suspend fun vote(voteDirection: Int, name: String) {
        repository.vote(voteDirection, name)
    }

    suspend fun download(comment: CommentDto) {
        repository.download(comment = comment)
    }

    suspend fun getAvatar(author: String): String? {

        val avatar: String?
        val result = repository.getUser(author)

        if (result.isSuccessful) {
            avatar = result.body()?.data?.iconImg
        } else avatar = null

        return avatar

    }
}