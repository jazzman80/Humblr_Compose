package com.skillbox.humblr.entity

import com.google.gson.annotations.SerializedName

data class ThingData(
    val id: String = "",
    val name: String = "",
    val title: String? = "",
    val author: String? = "",
    val created: Long? = 0L,
    val selftext: String? = "",

    @SerializedName("num_comments")
    val numComments: Int? = 0,

    val saved: Boolean? = false,
    val permalink: String? = "",
    val body: String? = "",
    //val subreddit: String?,
    val preview: PostPreview? = PostPreview(),

    @SerializedName("icon_img")
    val iconImg: String? = ""
) {

    fun toPostDataDto(): PostDto {
        return PostDto(
            id = id,
            name = name,
            title = title,
            author = author,
            created = created,
            selftext = selftext,
            numComments = numComments,
            saved = saved,
            permalink = permalink,
            //subreddit = subreddit,
            preview = preview
        )
    }

    fun toCommentDto(): CommentDto {
        return CommentDto(
            id = id,
            name = name,
            author = author,
            body = body,
            created = created
        )
    }

    fun toAccountDto(): AccountDto {
        return AccountDto(
            id = id,
            iconImg = iconImg
        )
    }
}
