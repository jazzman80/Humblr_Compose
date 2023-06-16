package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class SubredditData(
    val title: String,
    val id: String,
    val name: String,

    @SerializedName("public_description")
    val description: String,

    @SerializedName("header_img")
    val headerImg: String,

    @SerializedName("user_is_subscriber")
    val userIsSubscriber: Boolean
)
