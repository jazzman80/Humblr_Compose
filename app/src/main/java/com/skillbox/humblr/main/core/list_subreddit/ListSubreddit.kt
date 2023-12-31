package com.skillbox.humblr.main.core.list_subreddit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubredditListPreviewProvider
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ListSubreddit(
    pagingItems: LazyPagingItems<Subreddit>,
    onRefresh: () -> Unit = {},
    onSubscribe: (Boolean, String) -> Unit = { _, _ -> },
    onNavigate: (String) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when (pagingItems.loadState.refresh) {
            is NotLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        count = pagingItems.itemCount,
                        contentType = { pagingItems[it]?.data },
                        key = {
                            pagingItems[it]?.data?.id ?: ""
                        },
                        itemContent = {
                            if (pagingItems[it] != null) {
                                ItemSubreddit(
                                    item = pagingItems[it]!!.data,
                                    onSubscribe = { isSubscribed ->
                                        onSubscribe(isSubscribed, pagingItems[it]!!.data.name)
                                    },
                                    onClick = {
                                        onNavigate(pagingItems[it]!!.data.title)
                                    }
                                )

                            }
                        }
                    )

                    if (pagingItems.itemCount == 0) {
                        item {
                            Text(
                                text = stringResource(id = R.string.nothing_found),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            is Loading -> {
                CircularProgressIndicator()
            }

            is Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.network_error),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            onRefresh()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.refresh))
                    }
                }
            }
        }
    }
}

@ElementPreview
@Composable
fun PreviewListSubreddit(
    @PreviewParameter(SubredditListPreviewProvider::class) subsList: List<Subreddit>
) {

    val flow = MutableStateFlow(PagingData.from(subsList))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        ListSubreddit(
            pagingItems = lazyPagingItems,
            onSubscribe = { _, _ -> },
            onRefresh = {}
        )
    }
}