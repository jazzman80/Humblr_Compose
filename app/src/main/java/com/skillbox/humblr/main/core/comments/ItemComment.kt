package com.skillbox.humblr.main.core.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.CommentPreviewProvider
import com.skillbox.humblr.main.core.Avatar
import com.skillbox.humblr.main.core.DownloadButton
import com.skillbox.humblr.main.core.LikeButton
import com.skillbox.humblr.main.core.PublishedText
import com.skillbox.humblr.main.core.vote.VoteCheck
import com.skillbox.humblr.main.user.UserScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ItemComment(
    item: CommentDto = CommentDto(),
    onDownload: () -> Unit = {},
    onLiked: (Boolean) -> Unit = {}
) {

    val repository = koinInject<Repository>()
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<ItemCommentViewModel>()
    var avatar by remember { mutableStateOf("") }
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(true) {
//        avatar = viewModel.getAvatar(item.author ?: "")
        val result = repository.getUser(item.author!!)

        if (result.isSuccessful) {
            avatar = result.body()!!.data.iconImg ?: ""
        }
    }

    ItemCommentContent(
        item = item,
        onLiked = {
            scope.launch {
                viewModel.like(it, item.name)
            }
        },
        onVote = { voteDirection ->
            scope.launch {
                viewModel.vote(voteDirection, item.name)
            }
        },
        onDownload = { comment ->
            scope.launch {
                viewModel.download(comment)
            }
        },
        avatar = avatar,
        onAuthorClick = {
            navigator.push(UserScreen(item.author ?: ""))
        }
    )

}

@Composable
fun ItemCommentContent(
    item: CommentDto = CommentDto(),
    onDownload: (CommentDto) -> Unit = {},
    onLiked: (Boolean) -> Unit = {},
    onVote: (Int) -> Unit = {},
    avatar: String = "",
    onAuthorClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = 12.dp)
    ) {

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

//            if (LocalInspectionMode.current) {
//
//                Image(
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .size(30.dp), painter = painterResource(
//                        id = R.drawable.sample_avatar
//                    ), contentDescription = null
//                )
//
//            } else {
//
//                AsyncImage(
//                    model = avatar,
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .size(30.dp),
//                    contentDescription = null,
//                    placeholder = painterResource(
//                        id = R.drawable.avatar_placeholder
//                    )
//                )
//            }

            Avatar(
//                imageModel = { avatar }
                model = avatar
            )

            item.author?.let {
                Text(
                    modifier = Modifier
                        .clickable {
                            onAuthorClick()
                        }
                        .weight(1f),
                    text = it,
                    style = labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            item.created?.let {
                PublishedText(
                    publishTime = it,
                )
            }

        }

        item.body?.let {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {

                Divider(
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .width(1.dp)
                        .fillMaxHeight()
                )

                Text(
                    text = it,
                    style = bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(
                    start = 30.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            VoteCheck(
                votes = item.score ?: 0,
                onVote = onVote,
                likes = item.likes
            )

            DownloadButton(
                onDownload = {
                    onDownload(item)
                }
            )

            LikeButton(
                initState = item.saved ?: false,
                onClick = onLiked
            )
        }

//        ReplyList(
//            comments = item.replies?.data?.toCommentsList()
//        )

    }
}

@ElementPreview
@Composable
fun ItemCommentPreview(
    @PreviewParameter(CommentPreviewProvider::class) comment: CommentDto
) {
    AppTheme {
        ItemCommentContent(
            item = comment
        )
    }
}