package com.skillbox.humblr.main.core.comments

import androidx.lifecycle.ViewModel
import com.skillbox.humblr.core.Repository

class ItemCommentViewModel(
    private val repository: Repository
) : ViewModel() {

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

}