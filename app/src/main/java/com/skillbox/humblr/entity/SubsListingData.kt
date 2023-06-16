package com.skillbox.humblr.entity

import com.skillbox.humblr.entity.Subreddit

data class SubsListingData(
    val children: List<Subreddit>,
    val after: String
)