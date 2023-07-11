package com.skillbox.humblr.main.single_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.entity.PostDataPreviewProvider
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
    item: PostData,
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

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = item.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = labelLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                SubscribeButton(
                    onSubscribe = {},
                    initSubscribe = false
                )

                Text(
                    text = item.author,
                    color = MaterialTheme.colorScheme.primary,
                    style = labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Row {
                Text(
                    text = stringResource(id = R.string.published) + " ",
                    color = MaterialTheme.colorScheme.outline,
                    style = bodySmall
                )

                PublishedText(
                    publishTime = item.created
                )
            }
        }


        if (isImageVisible) {

            val source = item.preview?.images?.get(0)?.source

            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(
                        (source?.width?.toFloat() ?: 1f) / (source?.height?.toFloat() ?: 1f)
                    )
                    .fillMaxWidth(),
                model = source?.url,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(70.dp)
                    )
                },
                onError = {
                    isImageVisible = false
                }
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = item.selftext,
            color = MaterialTheme.colorScheme.onSurface,
            style = bodySmall
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Counter(
                    value = item.numComments,
                    style = bodySmall
                )

                Text(
                    text = " " + pluralStringResource(
                        id = R.plurals.comments,
                        count = if (item.numComments < 1000) item.numComments else 1000
                    ),
                    color = MaterialTheme.colorScheme.outline,
                    style = bodySmall
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = { onShare() }
                ) {
                    Text(
                        text = stringResource(id = R.string.share),
                        color = MaterialTheme.colorScheme.outline,
                        style = bodySmall,
                    )
                }

                LikeButton(
                    initState = item.saved,
                    onClick = {
                        onLike(it, item.name)
                    }
                )
            }
        }

    }
}

@ElementPreview
@Composable
fun PreviewSinglePostCard(
    @PreviewParameter(PostDataPreviewProvider::class) postData: PostData
) {
    AppTheme {

        SinglePostCard(
            item = postData
        )

    }
}