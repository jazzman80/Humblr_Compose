package com.skillbox.humblr.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.skillbox.humblr.main.feed.FeedScreen

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(
                    index = 0u,
                    title = ""
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(FeedScreen())
    }

}