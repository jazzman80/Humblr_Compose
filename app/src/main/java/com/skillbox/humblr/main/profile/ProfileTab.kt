package com.skillbox.humblr.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.skillbox.humblr.main.profile.me.MeScreen

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(
                    index = 2u,
                    title = ""
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(MeScreen())
    }

}