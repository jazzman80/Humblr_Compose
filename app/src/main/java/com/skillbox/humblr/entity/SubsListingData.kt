package com.skillbox.humblr.entity

data class SubsListingData(
    val children: List<Subreddit>,
    val after: String,
    val before: String
)