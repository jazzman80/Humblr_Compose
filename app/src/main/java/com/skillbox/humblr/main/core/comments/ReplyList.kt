package com.skillbox.humblr.main.core.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme


@Composable
fun ReplyList(
    comments: List<CommentDto>? = null,
    onDownload: (CommentDto) -> Unit = {},
    onRefresh: () -> Unit = {}
) {

    //val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        //state = rememberLazyListState()
    ) {
        repeat(comments?.size ?: 0) {
            ItemReply(
                item = comments!![it],
                onDownload = {
                    onDownload(comments[it])
                }
            )
        }

    }

}

@ElementPreview
@Composable
fun PreviewReplyList(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<CommentDto>
) {
    AppTheme {

        ReplyList(
            comments = comments
        )
    }
}