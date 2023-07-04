package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class PostData(
    val id: String = "",
    val title: String = "",
    val author: String = "",

    @SerializedName("num_comments")
    val numComments: Int = 0,

    val thumbnail: String = "",
    val selftext: String = "",
    val saved: Boolean = false,
    val name: String = "",
    val created: Long = 0L
)