package com.skillbox.humblr.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skillbox.humblr.R
import com.skillbox.humblr.main.favorites.FavoritesScreen
import com.skillbox.humblr.main.feed.FeedScreen
import com.skillbox.humblr.main.profile.ProfileScreen
import com.skillbox.humblr.main.search.SearchScreen
import com.skillbox.humblr.theme.AppTheme

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val feedRoute = "feed"
    val favoritesRoute = "favorites"
    val profileRoute = "profile"
    val searchRoute = "search"

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (content, navBar) = createRefs()
        var selectedItem by rememberSaveable {
            mutableStateOf(0)
        }
        val items = listOf(
            R.drawable.ic_feed,
            R.drawable.ic_favorites,
            R.drawable.ic_profile
        )

        NavHost(
            navController = navController,
            startDestination = feedRoute,
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(navBar.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(MaterialTheme.colorScheme.background)
        ) {
            composable(feedRoute) {
                FeedScreen(
                    navigateToSearch = { searchQuery ->
                        navController.navigate("$searchRoute/$searchQuery")
                    }
                )
            }
            composable(favoritesRoute) {
                FavoritesScreen()
            }
            composable(profileRoute) {
                ProfileScreen()
            }
            composable("$searchRoute/{searchQuery}") {
                val searchQuery = it.arguments?.getString("searchQuery")!!
//                val viewModel: SearchViewModel = hiltViewModel()
//                viewModel.setQuery(searchQuery)
                SearchScreen(
                    searchQuery = searchQuery,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .constrainAs(navBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {

            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index

                        with(navController) {
                            when (index) {
                                0 -> navigate(feedRoute)
                                1 -> navigate(favoritesRoute)
                                else -> navigate(profileRoute)
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


}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewMainScreen() {
    AppTheme {
        MainScreen()
    }
}