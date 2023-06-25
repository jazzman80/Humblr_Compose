package com.skillbox.humblr.main.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.skillbox.humblr.R
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.main.core.list_subreddit.ListSubreddit
import com.skillbox.humblr.theme.AppTheme

@Composable
fun SearchScreen(
    searchQuery: String,
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val query by rememberSaveable {
            mutableStateOf(searchQuery)
        }

        val subs = viewModel.repository.searchSubs(query).flow.collectAsLazyPagingItems()

        val (topBar, list) = createRefs()

        val startGuide = createGuidelineFromStart(0.08F)
        val endGuide = createGuidelineFromEnd(0.08F)

        TopBar(
            onNavIcon = { onBack() },
            titleText = stringResource(id = R.string.search_for) + " " + query,
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        )

        ListSubreddit(
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            pagingItems = subs,
            onRefreshButton = {
                viewModel.refreshToken()
                subs.refresh()
            },
            onSubscribe = { isSubscribed, name ->
                viewModel.subscribe(isSubscribed, name)
            }

        )

    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewSearchScreen() {
    AppTheme {
        //SearchScreen("Иди", {})
    }
}