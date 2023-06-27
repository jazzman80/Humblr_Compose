package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class PostData(
    val id: String,
    val title: String,
    val author: String,

    @SerializedName("num_comments")
    val numComments: Int,

    val thumbnail: String
)