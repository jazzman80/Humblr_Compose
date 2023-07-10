package com.skillbox.humblr.main

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.skillbox.humblr.main.favorites.FavoritesScreen
import com.skillbox.humblr.main.feed.FeedScreen
import com.skillbox.humblr.main.profile.ProfileScreen
import com.skillbox.humblr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Navigator(screen = FeedScreen()) { navigator ->

                    var selectedTabId by rememberSaveable { mutableStateOf(0) }

                    when (selectedTabId) {
                        0 -> navigator.push(FeedScreen())
                        1 -> navigator.push(FavoritesScreen())
                        else -> navigator.push(ProfileScreen())
                    }

                    MainScreen(
                        selectedTabId = selectedTabId,
                        onTabSelect = { selectedTabId = it }
                    ) {
                        CurrentScreen()
                    }
                }
            }
        }
    }
}