package com.skillbox.humblr.main.feed

import MainScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubredditListPreviewProvider
import com.skillbox.humblr.main.core.list_subreddit.ListSubreddit
import com.skillbox.humblr.main.posts.PostsScreen
import com.skillbox.humblr.main.search.SearchScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

class FeedScreen : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<FeedViewModel>()
        val newSubs = viewModel.newSubsFlow.collectAsLazyPagingItems()
        val popularSubs = viewModel.popularSubsFlow.collectAsLazyPagingItems()
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
        val onSearch: (String) -> Unit = remember {
            {
                navigator.push(SearchScreen(it))
            }
        }
        val onNewSubsRefresh = remember {
            {
                viewModel.refreshToken()
                newSubs.refresh()
            }
        }
        val onPopularSubsRefresh = remember {
            {
                viewModel.refreshToken()
                popularSubs.refresh()
            }
        }

        FeedScreenContent(
            newSubs = newSubs,
            popularSubs = popularSubs,
            onSearch = onSearch,
            onSubscribe = onSubscribe,
            onNewSubsRefresh = onNewSubsRefresh,
            onPopularSubsRefresh = onPopularSubsRefresh,
            onNavigate = onNavigate
        )

    }
}

@Composable
fun FeedScreenContent(
    newSubs: LazyPagingItems<Subreddit>,
    popularSubs: LazyPagingItems<Subreddit>,
    onSearch: (String) -> Unit = {},
    onSubscribe: (Boolean, String) -> Unit = { _, _ -> },
    onNewSubsRefresh: () -> Unit = {},
    onPopularSubsRefresh: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {

    var selectedTabId by rememberSaveable { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FeedSearchField(
            onSearch = onSearch
        )
        FeedTabs(
            selectedTabId = selectedTabId,
            onTabSelect = {
                selectedTabId = it
                if (it == 0) {
                    onPopularSubsRefresh()
                } else {
                    onNewSubsRefresh()
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (selectedTabId == 0) {
                ListSubreddit(
                    pagingItems = newSubs,
                    onSubscribe = onSubscribe,
                    onRefresh = onNewSubsRefresh,
                    onNavigate = onNavigate
                )
            } else {
                ListSubreddit(
                    pagingItems = popularSubs,
                    onSubscribe = onSubscribe,
                    onRefresh = onPopularSubsRefresh,
                    onNavigate = onNavigate
                )
            }
        }
    }
}

@ElementPreview
@Composable
fun PreviewFeedScreen(
    @PreviewParameter(SubredditListPreviewProvider::class) subsList: List<Subreddit>
) {
    AppTheme {
        val flow = MutableStateFlow(PagingData.from(subsList))
        val lazyPagingItems = flow.collectAsLazyPagingItems()

        SystemUI {
            MainScreen {
                FeedScreenContent(
                    newSubs = lazyPagingItems,
                    popularSubs = lazyPagingItems
                )
            }
        }
    }
}