package com.skillbox.humblr.core

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.skillbox.humblr.entity.Access
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubscribeResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import retrofit2.awaitResponse
import java.time.Instant
import java.util.Base64
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val apiService: ApiService
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

    // TODO Реализовать state - при запросе токена генерируется случайная строка и при получении токена проверяется соответствие
    private var state = "state"
    private val redirectUri = "com.skillboxpractice.humblr://humblr"
    private val duration = "permanent"
    private val scope = "read subscribe"
    private val authString = Base64.getEncoder().encodeToString("$clientId:".toByteArray())

    private var _accessToken = ""
    override val accessToken: String
        get() = _accessToken
    private val auth = "Bearer $_accessToken"

    private suspend fun validateAccessToken() {
        if (Instant.now().epochSecond >= expiresIn) {
            refreshToken()
        }
    }

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
            expiresIn = response.body()!!.expiresIn

            save()
        }
        return response
    }

    override fun getNewSubs(): Pager<String, Subreddit> {
        //validateAccessToken()
        return Pager(
            PagingConfig(pageSize)
        ) {
            NewSubsPagingSource("Bearer $_accessToken", apiService, pageSize)
        }

    }

    override fun getPopularSubs(): Pager<String, Subreddit> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            PopularSubsPagingSource("Bearer $_accessToken", apiService, pageSize)
        }
    }

    override fun searchSubs(query: String): Pager<String, Subreddit> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            SearchSubsPagingSource("Bearer $_accessToken", apiService, pageSize, query)
        }
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
                expiresIn = response.body()!!.expiresIn

                save()
            }
        }
    }

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

    private fun save() {
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