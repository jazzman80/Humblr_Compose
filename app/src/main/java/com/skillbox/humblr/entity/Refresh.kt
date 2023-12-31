package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class Refresh(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expiresIn: Long,

    val scope: String
)
