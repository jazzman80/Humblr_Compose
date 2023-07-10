package com.skillbox.humblr.main.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object FavoritesScreen : Tab {
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

        Text(
            text = "Favorites Screen",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }

}