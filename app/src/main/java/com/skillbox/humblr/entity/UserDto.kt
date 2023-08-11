package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class UserDto(
    val name: String? = null,

    @SerializedName("icon_img")
    val iconImg: String? = null,

    @SerializedName("is_friend")
    val isFriend: Boolean = false
)
