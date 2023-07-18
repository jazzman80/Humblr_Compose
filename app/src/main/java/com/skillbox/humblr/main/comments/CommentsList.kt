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
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.main.core.comments.ItemComment
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun CommentsList(
//    pagingItems: LazyPagingItems<Thing>,
    comments: List<CommentDto>? = null,
    onRefresh: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            items(
//                count = pagingItems.itemCount,
//                key = {
//                    pagingItems[it]?.data?.id ?: ""
//                }
//            ) {
//                if (pagingItems[it] != null) {
//
//                    ItemComment(
//                        item = pagingItems[it]!!.data.toCommentDto()
//                    )
//                }
//            }

            items(comments?.size ?: 0) {
                ItemComment(
                    item = comments!![it]
                )
            }
        }

//        if (pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0) {
//            Text(
//                text = stringResource(id = R.string.nothing_found),
//                textAlign = TextAlign.Center
//            )
//        }
//
//        if (pagingItems.loadState.refresh is LoadState.Loading) {
//            CircularProgressIndicator()
//        }
//
//        if (pagingItems.loadState.refresh is LoadState.Error) {
//            Column {
//                Text(
//                    text = stringResource(id = R.string.network_error),
//                    textAlign = TextAlign.Center
//                )
//
//                Button(
//                    onClick = onRefresh
//                ) {
//                    Text(text = stringResource(id = R.string.refresh))
//                }
//            }
//        }
    }

}

@ElementPreview
@Composable
fun PreviewCommentsList(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<CommentDto>
) {
    AppTheme {

//        val flow = MutableStateFlow(PagingData.from(comments))
//        val lazyPagingItems = flow.collectAsLazyPagingItems()

        CommentsList(
//            pagingItems = lazyPagingItems,
            comments = comments
        )
    }
}