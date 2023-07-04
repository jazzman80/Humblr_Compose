package com.skillbox.humblr.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.main.favorites.FavoritesScreen
import com.skillbox.humblr.main.feed.FeedScreen
import com.skillbox.humblr.main.profile.ProfileScreen
import com.skillbox.humblr.theme.AppTheme

@Composable
fun NavBar() {

    val items = listOf(
        R.drawable.ic_feed,
        R.drawable.ic_favorites,
        R.drawable.ic_profile
    )
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    val navigator = LocalNavigator.currentOrThrow

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.fillMaxWidth()
    ) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index

                    with(navigator) {
                        when (index) {
                            0 -> push(FeedScreen())
                            1 -> push(FavoritesScreen())
                            else -> push(ProfileScreen())
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = item
                        ),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewNavBar() {
    AppTheme {
        NavBar()
    }
}