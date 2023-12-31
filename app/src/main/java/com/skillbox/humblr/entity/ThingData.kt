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

    val saved: Boolean? = null,
    val permalink: String? = "",
    val body: String? = "",
    //val subreddit: String?,
    val preview: PostPreview? = PostPreview(),

    @SerializedName("icon_img")
    val iconImg: String? = "",

//    @JsonAdapter(EmptyStringAsNullTypeAdapter::class)
//    val replies: Listing? = null,

    val score: Int? = null,
    val likes: Boolean? = null,

    @SerializedName("is_friend")
    val isFriend: Boolean? = null
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
            created = created,
//            replies = replies,
            saved = saved,
            score = score,
            likes = likes
        )
    }

    fun toAccountDto(): AccountDto {
        return AccountDto(
            id = id,
            iconImg = iconImg
        )
    }

    fun toUserDto(): UserDto {
        return UserDto(
            name = name,
            iconImg = iconImg ?: "",
            isFriend = isFriend ?: false,
            id = id
        )
    }

}
