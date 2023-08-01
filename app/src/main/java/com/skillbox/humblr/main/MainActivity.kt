package com.skillbox.humblr.main

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.skillbox.humblr.main.favorites.FavoritesTab
import com.skillbox.humblr.main.navigation.HomeTab
import com.skillbox.humblr.main.profile.ProfileScreen
import com.skillbox.humblr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                TabNavigator(HomeTab) { navigator ->

                    var selectedTabId by rememberSaveable { mutableStateOf(0) }

                    when (selectedTabId) {
                        0 -> navigator.current = HomeTab
                        1 -> navigator.current = FavoritesTab
                        else -> navigator.current = ProfileScreen
                    }

                    MainScreen(
                        selectedTabId = selectedTabId,
                        onTabSelect = { selectedTabId = it }
                    ) {
                        CurrentTab()
                    }
                }
            }
        }
    }
}