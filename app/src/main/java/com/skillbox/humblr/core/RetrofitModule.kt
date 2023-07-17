package com.skillbox.humblr.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skillbox.humblr.entity.CommentsResponse
import com.skillbox.humblr.entity.CommentsResponseSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(CommentsResponse::class.java, CommentsResponseSerializer())
        .create()

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://oauth.reddit.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }
}