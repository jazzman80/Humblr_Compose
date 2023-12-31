package com.skillbox.humblr.main.search

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
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
import org.koin.androidx.compose.koinViewModel

data class SearchScreen(val searchQuery: String) : AndroidScreen() {

    @Composable
    override fun Content() {

        val viewModel = koinViewModel<SearchViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val subs = viewModel.getSubs(searchQuery).collectAsLazyPagingItems()
        val onBack: () -> Unit = remember {
            {
                navigator.pop()
            }
        }
        val onRefresh: () -> Unit = remember {
            {
                viewModel.refreshToken()
                subs.refresh()
            }
        }
        val onSubscribe: (Boolean, String) -> Unit = remember {
            { isSubscribed, name ->
                viewModel.subscribe(isSubscribed, name)
            }
        }
        val onNavigate: (String) -> Unit = remember {
            {
                navigator.push(PostsScreen(it))
            }
        }

        SearchScreenContent(
            query = searchQuery,
            subs = subs,
            onBack = onBack,
            onRefresh = onRefresh,
            onSubscribe = onSubscribe,
            onNavigate = onNavigate
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
