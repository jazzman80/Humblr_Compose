package com.skillbox.humblr.main.single_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.PostDataPreviewProvider
import com.skillbox.humblr.entity.PostDto
import com.skillbox.humblr.main.core.Counter
import com.skillbox.humblr.main.core.LikeButton
import com.skillbox.humblr.main.core.PublishedText
import com.skillbox.humblr.main.core.list_subreddit.SubscribeButton
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun SinglePostCard(
    item: PostDto,
    onLike: (Boolean, String) -> Unit = { _, _ -> },
    onShare: () -> Unit = {}
) {

    var isImageVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(all = 12.dp)
            .clip(
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(all = 12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item.title?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                style = labelLarge
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {

                item.author?.let {
                    SubscribeButton(
                        onSubscribe = {
                            //TODO
                        },
                        initSubscribe = false
                    )

                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        style = labelLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

            }

            item.created?.let {
                Row {
                    Text(
                        text = stringResource(id = R.string.published) + " ",
                        color = MaterialTheme.colorScheme.outline,
                        style = bodySmall
                    )

                    PublishedText(
                        publishTime = it
                    )
                }
            }

        }


//        if (isImageVisible) {

        val source = item.preview?.images?.first()?.source

        SubcomposeAsyncImage(
            // modifier = Modifier
//                    .aspectRatio(
//                        (source?.width?.toFloat() ?: 1f) / (source?.height?.toFloat() ?: 1f)
//                    )
            // .fillMaxWidth(),
            model = source?.url,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(70.dp)
                )
            }
        )
//        }

        item.selftext?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                style = bodySmall
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                item.numComments?.let {
                    Counter(
                        value = it,
                        style = bodySmall
                    )

                    Text(
                        text = " " + pluralStringResource(
                            id = R.plurals.comments,
                            count = if (it < 1000) it else 1000
                        ),
                        color = MaterialTheme.colorScheme.outline,
                        style = bodySmall
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                item.permalink?.let {
                    TextButton(
                        onClick = { onShare() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.share),
                            color = MaterialTheme.colorScheme.outline,
                            style = bodySmall,
                        )
                    }
                }

                item.saved?.let { isSaved ->
                    LikeButton(
                        initState = isSaved,
                        onClick = {
                            onLike(it, item.name)
                        }
                    )
                }

            }
        }

    }
}

@ElementPreview
@Composable
fun PreviewSinglePostCard(
    @PreviewParameter(PostDataPreviewProvider::class) post: PostDto
) {
    AppTheme {

        SinglePostCard(
            item = post
        )

    }
}