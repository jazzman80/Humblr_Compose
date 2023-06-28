package com.skillbox.humblr.core

import android.net.Uri
import androidx.paging.Pager
import com.skillbox.humblr.entity.Access
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubscribeResponse
import retrofit2.Response

interface Repository {

    val accessToken: String
    val isOnboardDone: Boolean
    fun onboardDone()
    fun composeUrl(): Uri?
    suspend fun getAccessToken(authCode: String): Response<Access>
    fun getNewSubs(): Pager<String, Subreddit>
    fun getPopularSubs(): Pager<String, Subreddit>
    fun searchSubs(query: String): Pager<String, Subreddit>
    fun getPosts(title: String): Pager<String, Post>
    suspend fun refreshToken()
    suspend fun unsubscribe(fullName: String?): Response<SubscribeResponse>
    suspend fun subscribe(fullName: String?): Response<SubscribeResponse>
    suspend fun unsave(name: String): Response<SubscribeResponse>
    suspend fun save(name: String): Response<SubscribeResponse>


}