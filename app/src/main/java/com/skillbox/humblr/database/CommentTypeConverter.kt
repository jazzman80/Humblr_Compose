package com.skillbox.humblr.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.skillbox.humblr.entity.Listing

class CommentTypeConverter {
    @TypeConverter
    fun fromReplies(replies: Listing): String {
        return Gson().toJson(replies)
    }

    @TypeConverter
    fun toReplies(string: String): Listing {
        return Gson().fromJson(string, Listing::class.java)
    }

}