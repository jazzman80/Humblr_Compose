package com.skillbox.humblr.main.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedModel : ViewModel() {

    val count: LiveData<Int> get() = _count
    private val _count = MutableLiveData(0)

    fun addCount() {
        _count.value = _count.value?.plus(1)
    }
}