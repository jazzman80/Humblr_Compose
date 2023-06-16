package com.skillbox.humblr.core

import com.skillbox.humblr.core.ApiService
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

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://oauth.reddit.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}