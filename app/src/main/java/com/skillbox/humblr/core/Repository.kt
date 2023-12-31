package com.skillbox.humblr.core

import android.net.Uri
import androidx.paging.Pager
import com.skillbox.humblr.entity.Access
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.EmptyResponse
import com.skillbox.humblr.entity.Listing
import com.skillbox.humblr.entity.MeDto
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostListing
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubscribeResponse
import com.skillbox.humblr.entity.Thing
import com.skillbox.humblr.entity.UserListDto
import retrofit2.Response

interface Repository {

    val accessToken: String
    val isOnboardDone: Boolean
    val me: MeDto
    fun onboardDone()
    fun composeUrl(): Uri?
    suspend fun getAccessToken(authCode: String): Response<Access>
    fun getNewSubs(): Pager<String, Subreddit>
    fun getPopularSubs(): Pager<String, Subreddit>
    fun searchSubs(query: String): Pager<String, Subreddit>
    fun getPosts(title: String): Pager<String, Post>
    fun getFavoriteSubs(): Pager<String, Subreddit>
    fun getSavedComments(): Pager<String, CommentDto>
    fun getUserComments(username: String): Pager<String, CommentDto>

    //    fun getComments(article: String): Pager<String, Thing>
    suspend fun refreshToken()
    suspend fun unsubscribe(fullName: String?): Response<SubscribeResponse>
    suspend fun subscribe(fullName: String?): Response<SubscribeResponse>
    suspend fun unsave(name: String): Response<SubscribeResponse>
    suspend fun save(name: String): Response<SubscribeResponse>
    suspend fun getSinglePost(name: String): Response<PostListing>
    suspend fun getPostWithComment(article: String): Response<List<Listing>>
    suspend fun getComments(article: String): Response<List<Listing>>
    suspend fun getMoreComments(article: String): Response<Listing>
    suspend fun getUser(username: String): Response<Thing>
    suspend fun download(comment: CommentDto)
    suspend fun clearDownloaded()
    suspend fun getAllComments(): List<CommentDto>
    suspend fun vote(voteDirection: Int, name: String): Response<SubscribeResponse>
    suspend fun getUsername()
    suspend fun exit()
    suspend fun becomeFriends(username: String): Response<EmptyResponse>
    suspend fun stopBeingFriends(username: String): Response<EmptyResponse>
    suspend fun getFriends(): Response<UserListDto>
}