package com.skillbox.humblr.main.single_post

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
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

}