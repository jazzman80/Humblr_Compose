package com.skillbox.humblr.entity

data class ListingData(
    val children: List<Thing>,
    val after: String?
)
