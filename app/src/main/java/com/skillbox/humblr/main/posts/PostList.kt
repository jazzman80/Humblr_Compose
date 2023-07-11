package com.skillbox.humblr.main.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostListPreviewProvider
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PostList(
    pagingItems: LazyPagingItems<Post>,
    onRefreshButton: () -> Unit = {},
    onSave: (Boolean, String) -> Unit = { _, _ -> },
    onNavigate: (String) -> Unit = {}
) {

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {

            items(
                count = pagingItems.itemCount,
                key = {
                    pagingItems[it]?.data?.id ?: ""
                }
            ) {
                if (pagingItems[it] != null) {
                    ItemPost(
                        item = pagingItems[it]!!.data,
                        onSave = { isSaved ->
                            onSave(isSaved, pagingItems[it]!!.data.name)
                        },
                        onNavigate = {
                            onNavigate(pagingItems[it]!!.data.name)
                        }
                    )
                } else {
                    //ItemSubredditPlaceholder()
                }
            }
        }

        if (pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0) {
            Text(
                text = stringResource(id = R.string.nothing_found),
                textAlign = TextAlign.Center
            )
        }

        if (pagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator()
        }

        if (pagingItems.loadState.refresh is LoadState.Error) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.network_error),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        onRefreshButton()
                    }
                ) {
                    Text(text = stringResource(id = R.string.refresh))
                }
            }
        }
    }
}

@ElementPreview
@Composable
fun PostListPreview(
    @PreviewParameter(PostListPreviewProvider::class) postList: List<Post>
) {

    val flow = MutableStateFlow(PagingData.from(postList))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        PostList(
            pagingItems = lazyPagingItems
        )
    }
}
