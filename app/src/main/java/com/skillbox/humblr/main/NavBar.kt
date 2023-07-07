package com.skillbox.humblr.main

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
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun NavBar(
    modifier: Modifier
) {

    val items = listOf(
        R.drawable.ic_feed,
        R.drawable.ic_favorites,
        R.drawable.ic_profile
    )
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.fillMaxWidth()
    ) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index

//                    with(navigator) {
//                        when (index) {
//                            0 -> push(FeedScreen())
//                            1 -> push(FavoritesScreen())
//                            else -> push(ProfileScreen())
//                        }
//                    }
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

@ElementPreview
@Composable
fun PreviewNavBar() {
    AppTheme {
        NavBar(Modifier)
    }
}