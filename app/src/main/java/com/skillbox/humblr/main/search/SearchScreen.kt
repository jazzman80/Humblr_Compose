package com.skillbox.humblr.main.search

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubredditListPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.main.core.list_subreddit.ListSubreddit
import com.skillbox.humblr.main.posts.PostsScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchScreen(val searchQuery: String) : AndroidScreen() {

    @Composable
    override fun Content() {

        val viewModel = getViewModel<SearchViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val subs = viewModel.getSubs(searchQuery).collectAsLazyPagingItems()

        SearchScreenContent(
            query = searchQuery,
            subs = subs,
            onBack = { navigator.pop() },
            onRefresh = {
                viewModel.refreshToken()
                subs.refresh()
            },
            onSubscribe = { isSubscribed, name ->
                viewModel.subscribe(isSubscribed, name)
            },
            onNavigate = {
                navigator.push(PostsScreen(it))
            }
        )
    }
}

@Composable
fun SearchScreenContent(
    query: String = "",
    subs: LazyPagingItems<Subreddit>,
    onBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onSubscribe: (Boolean, String) -> Unit = { _, _ -> },
    onNavigate: (String) -> Unit = {}
) {
    Column {
        TopBar(
            titleText = stringResource(id = R.string.search_for) + " " + query,
            onBack = onBack
        )
        ListSubreddit(
            pagingItems = subs,
            onRefresh = onRefresh,
            onSubscribe = onSubscribe,
            onNavigate = onNavigate
        )
    }
}

@ElementPreview
@Composable
fun PreviewSearchScreen(
    @PreviewParameter(SubredditListPreviewProvider::class) subsList: List<Subreddit>
) {
    AppTheme {
        val flow = MutableStateFlow(PagingData.from(subsList))
        val lazyPagingItems = flow.collectAsLazyPagingItems()

        SystemUI {
            MainScreen {
                SearchScreenContent(
                    query = "Длиннопост",
                    subs = lazyPagingItems
                )
            }
        }
    }
}
