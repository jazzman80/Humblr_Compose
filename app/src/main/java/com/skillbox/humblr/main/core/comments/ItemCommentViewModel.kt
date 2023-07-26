package com.skillbox.humblr.main.core.comments

import androidx.lifecycle.ViewModel
import com.skillbox.humblr.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemCommentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun like(liked: Boolean, name: String) {
        if (liked) {
            repository.unsave(name)
        } else {
            repository.save(name)
        }
    }

}