package com.skillbox.humblr.entity

data class PostListingData(
    val children: List<Post>,
    val after: String,
    val before: String
)
