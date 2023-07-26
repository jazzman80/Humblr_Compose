package com.skillbox.humblr.entity

data class ListingData(
    val children: List<Thing>,
    val after: String?
) {
    fun toCommentsList(): List<CommentDto> {

        val commentList = mutableListOf<CommentDto>()

        for (thing in children) {
            commentList.add(thing.data.toCommentDto())
        }

        return commentList
    }
}
