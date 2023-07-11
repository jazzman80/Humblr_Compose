package com.skillbox.humblr.main.single_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.PostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SinglePostViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        refreshToken()
    }

    val postData: LiveData<PostData>
        get() = _postData
    private val _postData = MutableLiveData<PostData>()

    fun getPost(name: String) {
        viewModelScope.launch {

            val response = repository.getSinglePost(name)

            if (response.isSuccessful) {
                _postData.value = response.body()?.data?.children?.get(0)?.data
            }
        }
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
}