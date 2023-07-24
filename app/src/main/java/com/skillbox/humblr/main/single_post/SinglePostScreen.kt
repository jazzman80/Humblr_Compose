package com.skillbox.humblr.main.single_post

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.PostDataPreviewProvider
import com.skillbox.humblr.entity.PostDto
import com.skillbox.humblr.main.comments.CommentsScreen
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.main.core.comments.ItemComment
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

data class SinglePostScreen(
    val id: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<SinglePostViewModel>()

        val post by viewModel.post.observeAsState()
        val comment by viewModel.comment.observeAsState()
        val context = LocalContext.current

        SinglePostScreenContent(
            post = post,
            comment = comment ?: CommentDto(),
            onBack = {
                navigator.pop()
            },
            onLike = { isLiked, name ->
                viewModel.save(isLiked, name)
            },
            onShare = {
                viewModel.share(context, post?.permalink ?: "")
            },
            onShowComments = {
                navigator.push(CommentsScreen(post?.id ?: ""))
            },
            onDownload = {
                viewModel.download(it)
            }
        )

        LaunchedEffect(key1 = true) {
            viewModel.getPostWithComment(id)
        }
    }
}

@Composable
fun SinglePostScreenContent(
    post: PostDto?,
    comment: CommentDto,
    onBack: () -> Unit = {},
    onLike: (Boolean, String) -> Unit = { _, _ -> },
    onShare: () -> Unit = {},
    onShowComments: () -> Unit = {},
    onDownload: (CommentDto) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopBar(
            titleText = post?.subreddit ?: "",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            SinglePostCard(
                item = post ?: PostDto(),
                onLike = onLike,
                onShare = onShare
            )

            if ((post?.numComments ?: 0) > 0) {
                ItemComment(
                    item = comment,
                    onDownload = {
                        onDownload(comment)
                    }
                )
            }

            if ((post?.numComments ?: 0) > 1) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                    onClick = {
                        onShowComments()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.show_all)
                    )
                }
            }
        }
    }
}

@ElementPreview
@Composable
fun PreviewSinglePostScreen(
    @PreviewParameter(PostDataPreviewProvider::class) post: PostDto
) {
    AppTheme {
        SystemUI {
            MainScreen {
                SinglePostScreenContent(
                    post = post,
                    comment = CommentDto()
                )
            }
        }

    }
}