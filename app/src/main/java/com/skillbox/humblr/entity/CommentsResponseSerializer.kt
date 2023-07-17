package com.skillbox.humblr.entity

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


class CommentsResponseSerializer : JsonSerializer<CommentsResponse> {
    override fun serialize(
        src: CommentsResponse?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {

        val result = JsonArray()



        return result
    }
}