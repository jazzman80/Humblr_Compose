package com.skillbox.humblr.core

import com.skillbox.humblr.entity.Access
import com.skillbox.humblr.entity.Listing
import com.skillbox.humblr.entity.PostListing
import com.skillbox.humblr.entity.Refresh
import com.skillbox.humblr.entity.SubsListing
import com.skillbox.humblr.entity.SubscribeResponse
import com.skillbox.humblr.entity.Thing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @POST
    fun getAccessToken(
        @Url url: String,
        @Header("Authorization") auth: String,
        @Query("grant_type") grantType: String,
        @Query("code") authCode: String,
        @Query("redirect_uri") redirectUri: String
    ): Call<Access>

    @POST
    fun refreshToken(
        @Url url: String,
        @Header("Authorization") auth: String,
        @Query("grant_type") grantType: String,
        @Query("refresh_token") refreshToken: String
    ): Call<Refresh>

    @GET("/subreddits/new")
    fun getNewSubs(
        @Header("Authorization") auth: String,
        @Query("after") after: String?,
        @Query("limit") limit: Int
    ): Call<SubsListing>

    @GET("/subreddits/popular")
    fun getPopularSubs(
        @Header("Authorization") auth: String,
        @Query("after") after: String?,
        @Query("limit") limit: Int
    ): Call<SubsListing>

    @GET("/subreddits/search")
    fun searchSubs(
        @Header("Authorization") auth: String,
        @Query("after") after: String?,
        @Query("limit") limit: Int,
        @Query("q") query: String
    ): Call<SubsListing>

    @POST("api/subscribe")
    fun subscribe(
        @Header("Authorization") auth: String,
        @Query("action") action: String?,
        @Query("sr") sr: String?
    ): Call<SubscribeResponse>

    @GET("r/{title}")
    fun getPosts(
        @Header("Authorization") auth: String,
        @Path("title") title: String,
        @Query("after") after: String?,
        @Query("limit") limit: Int,
        @Query("raw_json") rawJson: Int = 1
    ): Call<PostListing>

    @POST("api/save")
    fun save(
        @Header("Authorization") auth: String,
        @Query("id") id: String
    ): Call<SubscribeResponse>

    @POST("api/unsave")
    fun unsave(
        @Header("Authorization") auth: String,
        @Query("id") id: String
    ): Call<SubscribeResponse>

    @GET("by_id/{names}")
    fun getSinglePost(
        @Header("Authorization") auth: String,
        @Path("names") names: String
    ): Call<PostListing>

    @GET("comments/{article}")
    fun getPostWithComment(
        @Header("Authorization") auth: String,
        @Path("article") article: String,
        @Query("limit") limit: Int = 1,
        @Query("raw_json") rawJson: Int = 1
    ): Call<List<Listing>>

    @GET("user/{username}/about")
    fun getUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String,
        @Query("raw_json") rawJson: Int = 1
    ): Call<Thing>
}