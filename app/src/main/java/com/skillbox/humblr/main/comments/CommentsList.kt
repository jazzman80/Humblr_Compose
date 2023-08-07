package com.skillbox.humblr.main.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.main.core.comments.ItemComment
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun CommentsList(
    comments: List<CommentDto>? = null,
    lazyComments: LazyPagingItems<CommentDto>? = null,
    onDownload: (CommentDto) -> Unit = {},
    onRefresh: () -> Unit = {}
) {

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (comments != null) {
                items(comments.size) {
                    ItemComment(
                        item = comments[it],
                        onDownload = {
                            onDownload(comments[it])
                        }
                    )
                }
            } else if (lazyComments != null) {
                items(
                    count = lazyComments.itemCount,
                    key = {
                        lazyComments[it]?.id ?: ""
                    }
                ) {
                    if (lazyComments[it] != null) {
                        ItemComment(
                            item = lazyComments[it]!!,
                            onDownload = {
                                onDownload(lazyComments[it]!!)
                            }
                        )
                    } else {
                        //ItemSubredditPlaceholder()
                    }
                }
            }

        }

    }

}

@ElementPreview
@Composable
fun PreviewCommentsList(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<CommentDto>
) {
    AppTheme {

        CommentsList(
            comments = comments
        )
    }
}