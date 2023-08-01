package com.skillbox.humblr.main.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(
                    index = 1u,
                    title = ""
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(FavoritesScreen())
    }

}