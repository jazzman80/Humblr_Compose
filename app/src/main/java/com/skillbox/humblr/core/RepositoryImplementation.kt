package com.skillbox.humblr.core

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.skillbox.humblr.core.PagingSources.FavoriteSubsPagingSource
import com.skillbox.humblr.core.PagingSources.NewSubsPagingSource
import com.skillbox.humblr.core.PagingSources.PopularSubsPagingSource
import com.skillbox.humblr.core.PagingSources.SearchSubsPagingSource
import com.skillbox.humblr.core.paging_sources.SavedCommentsPagingSource
import com.skillbox.humblr.core.paging_sources.UserCommentsPagingSource
import com.skillbox.humblr.database.CommentDao
import com.skillbox.humblr.entity.Access
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.EmptyResponse
import com.skillbox.humblr.entity.FriendCallBody
import com.skillbox.humblr.entity.Listing
import com.skillbox.humblr.entity.MeDto
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostListing
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubscribeResponse
import com.skillbox.humblr.entity.Thing
import com.skillbox.humblr.entity.UserListDto
import retrofit2.Response
import retrofit2.awaitResponse
import java.time.Instant
import java.util.Base64

class RepositoryImplementation(
    private val appContext: Context,
    private val apiService: ApiService,
    private val commentDao: CommentDao
) : Repository {

    private val preferenceFileKey = "PREFERENCE_FILE_KEY"
    private val onboardKey = "ONBOARD_KEY"
    private val accessTokenKey = "ACCESS_TOKEN_KEY"
    private val refreshTokenKey = "REFRESH_TOKEN_KEY"
    private val expiresInKey = "EXPIRES_IN_KEY"
    private val authUri = "https://old.reddit.com/api/v1/authorize"
    private val tokenUri = "https://www.reddit.com/api/v1/access_token"
    private val clientId = "WVbTDJRNnWgywvGrYAkyRw"
    private val responseType = "code"
    private val pageSize = 25
    override lateinit var me: MeDto
    private val pagingConfig = PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = true
    )

    // TODO Реализовать state - при запросе токена генерируется случайная строка и при получении токена проверяется соответствие
    private var state = "state"
    private val redirectUri = "com.skillboxpractice.humblr://humblr"
    private val duration = "permanent"
    private val scope = "read subscribe save vote mysubreddits identity history"
    private val authString = Base64.getEncoder().encodeToString("$clientId:".toByteArray())

    private var _accessToken = ""
    override val accessToken: String
        get() = _accessToken

    private var refreshToken = ""
    private var expiresIn = 0L

    private var _isOnboardDone = false
    override val isOnboardDone get() = _isOnboardDone

    init {

        // Загружаем состояние онбординга и токены
        val sharedPreferences =
            appContext.getSharedPreferences(preferenceFileKey, AppCompatActivity.MODE_PRIVATE)
        _isOnboardDone = sharedPreferences.getBoolean(onboardKey, false)

        if (sharedPreferences.contains(accessTokenKey))
            _accessToken = sharedPreferences.getString(accessTokenKey, "")!!
        if (sharedPreferences.contains(refreshTokenKey))
            refreshToken = sharedPreferences.getString(refreshTokenKey, "")!!

        expiresIn = sharedPreferences.getLong(expiresInKey, 0L)

    }

    override fun onboardDone() {
        _isOnboardDone = true

        // Сохраняем состояние онбординга
        val edit =
            appContext.getSharedPreferences(
                preferenceFileKey,
                AppCompatActivity.MODE_PRIVATE
            ).edit()
        edit.putBoolean(onboardKey, _isOnboardDone).apply()
        edit.clear()
    }

    override fun composeUrl(): Uri? {
        return Uri.parse(authUri)
            .buildUpon()
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("response_type", responseType)
            .appendQueryParameter("state", state)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("duration", duration)
            .appendQueryParameter("scope", scope)
            .build()
    }

    override suspend fun getAccessToken(authCode: String): Response<Access> {

        val response = apiService.getAccessToken(
            tokenUri,
            "Basic $authString",
            "authorization_code",
            authCode,
            redirectUri
        ).awaitResponse()

        if (response.isSuccessful) {
            _accessToken = response.body()!!.accessToken
            refreshToken = response.body()!!.refreshToken
            expiresIn = response.body()!!.expiresIn + Instant.now().epochSecond

            saveToken()
        }
        return response
    }

    override suspend fun refreshToken() {
        if (Instant.now().epochSecond >= expiresIn) {
            val response = apiService.refreshToken(
                tokenUri,
                "Basic $authString",
                "refresh_token",
                refreshToken
            ).awaitResponse()

            if (response.isSuccessful) {
                _accessToken = response.body()!!.accessToken
                expiresIn = response.body()!!.expiresIn + Instant.now().epochSecond

                saveToken()
            }
        }
    }

    override fun getNewSubs(): Pager<String, Subreddit> {
        return Pager(
            pagingConfig
        ) {
            NewSubsPagingSource("Bearer $_accessToken", apiService, pageSize)
        }

    }

    override fun getPopularSubs(): Pager<String, Subreddit> {
        return Pager(
            pagingConfig
        ) {
            PopularSubsPagingSource("Bearer $_accessToken", apiService, pageSize)
        }
    }

    override fun searchSubs(query: String): Pager<String, Subreddit> {
        return Pager(
            pagingConfig
        ) {
            SearchSubsPagingSource("Bearer $_accessToken", apiService, pageSize, query)
        }
    }

    override fun getPosts(title: String): Pager<String, Post> {
        return Pager(
            pagingConfig
        ) {
            PostPagingSource("Bearer $_accessToken", apiService, pageSize, title)
        }
    }

    override fun getFavoriteSubs(): Pager<String, Subreddit> {
        return Pager(
            pagingConfig
        ) {
            FavoriteSubsPagingSource("Bearer $_accessToken", apiService, pageSize)
        }
    }

    override fun getSavedComments(): Pager<String, CommentDto> {
        return Pager(
            pagingConfig
        ) {
            SavedCommentsPagingSource(me.name, "Bearer $_accessToken", apiService, pageSize)
        }
    }

    override fun getUserComments(username: String): Pager<String, CommentDto> {
        return Pager(
            pagingConfig
        ) {
            UserCommentsPagingSource(username, "Bearer $_accessToken", apiService, pageSize)
        }
    }

//    override fun getComments(article: String): Pager<String, Thing> {
//        return Pager(
//            PagingConfig(pageSize)
//        ) {
//            CommentsPagingSource(article, "Bearer $_accessToken", apiService, 5)
//        }
//    }


    override suspend fun unsubscribe(fullName: String?): Response<SubscribeResponse> {
        return apiService.subscribe(
            "Bearer $_accessToken",
            "unsub",
            fullName
        ).awaitResponse()
    }

    override suspend fun subscribe(fullName: String?): Response<SubscribeResponse> {
        return apiService.subscribe(
            "Bearer $_accessToken",
            "sub",
            fullName
        ).awaitResponse()
    }

    override suspend fun unsave(name: String): Response<SubscribeResponse> {
        return apiService.unsave(
            "Bearer $_accessToken",
            name
        ).awaitResponse()
    }

    override suspend fun save(name: String): Response<SubscribeResponse> {
        return apiService.save(
            "Bearer $_accessToken",
            name
        ).awaitResponse()
    }

    override suspend fun getSinglePost(name: String): Response<PostListing> {
        return apiService.getSinglePost(
            "Bearer $_accessToken",
            name
        ).awaitResponse()
    }

    override suspend fun getPostWithComment(
        article: String
    ): Response<List<Listing>> {
        return apiService.getPostWithComment(
            "Bearer $_accessToken",
            article
        ).awaitResponse()
    }

    override suspend fun getComments(
        article: String
    ): Response<List<Listing>> {
        return apiService.getPostWithComment(
            auth = "Bearer $_accessToken",
            article = article,
            limit = 50000
        ).awaitResponse()
    }

    override suspend fun getMoreComments(
        article: String
    ): Response<Listing> {
        return apiService.getMoreComments(
            auth = "Bearer $_accessToken",
            linkId = article,
        ).awaitResponse()
    }


    override suspend fun getUser(username: String): Response<Thing> {
        return apiService.getUser(
            "Bearer $_accessToken",
            username
        ).awaitResponse()
    }

    override suspend fun download(comment: CommentDto) {
        commentDao.insert(comment)
    }

    override suspend fun clearDownloaded() {
        commentDao.clearTable()
    }

    override suspend fun getAllComments(): List<CommentDto> {
        return commentDao.selectAll()
    }

    override suspend fun vote(voteDirection: Int, name: String): Response<SubscribeResponse> {
        return apiService.vote(
            auth = "Bearer $_accessToken",
            dir = voteDirection,
            id = name
        ).awaitResponse()
    }

    override suspend fun getUsername() {
        val result = apiService.getMe(
            auth = "Bearer $_accessToken"
        ).awaitResponse()

        if (result.isSuccessful) {
            me = result.body()!!
        }
    }

    override suspend fun exit() {
        val edit =
            appContext.getSharedPreferences(
                preferenceFileKey,
                AppCompatActivity.MODE_PRIVATE
            ).edit()
        edit.remove(accessTokenKey).apply()
        edit.remove(refreshTokenKey).apply()
        edit.remove(expiresInKey).apply()
        edit.clear()
    }

    override suspend fun becomeFriends(username: String): Response<EmptyResponse> {
        return apiService.becomeFriends(
            auth = "Bearer $_accessToken",
            username = username,
            body = FriendCallBody()
        ).awaitResponse()
    }

    override suspend fun stopBeingFriends(username: String): Response<EmptyResponse> {
        return apiService.stopBeingFriends(
            auth = "Bearer $_accessToken",
            username = username
        ).awaitResponse()
    }

    override suspend fun getFriends(): Response<UserListDto> {
        return apiService.getFriendList(
            auth = "Bearer $_accessToken"
        ).awaitResponse()
    }

    private fun saveToken() {
        // Сохраняем токены
        val edit =
            appContext.getSharedPreferences(
                preferenceFileKey,
                AppCompatActivity.MODE_PRIVATE
            ).edit()
        edit.putString(accessTokenKey, _accessToken).apply()
        edit.putString(refreshTokenKey, refreshToken).apply()
        edit.putLong(expiresInKey, expiresIn).apply()
        edit.clear()
    }
}