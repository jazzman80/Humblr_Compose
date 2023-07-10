package com.skillbox.humblr.main.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.Visibility.Companion.Visible
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.entity.PostDataPreviewProvider
import com.skillbox.humblr.main.core.Counter
import com.skillbox.humblr.main.core.list_subreddit.SubscribeButton
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun ItemPost(
    item: PostData,
    onSave: (Boolean) -> Unit = {},
    navigateToPost: () -> Unit = {}
) {

    var imageVisibility by remember {
        mutableStateOf(Visible)
    }

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { navigateToPost() }
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(all = 12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = item.title,
                style = labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            SubscribeButton(
                onSubscribe = onSave,
                initSubscribe = item.saved
            )

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(item.thumbnail)
                    .placeholder(R.drawable.image_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onError = { imageVisibility = Gone }
            )

            Text(
                text = item.selftext,
                style = bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {

            Text(
                modifier = Modifier
                    .weight(1f),
                text = item.author,
                style = bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Counter(
                    value = item.numComments,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_comments),
                    contentDescription = "comments",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

    }
}

@ElementPreview
@Composable
fun PreviewItemPost(
    @PreviewParameter(PostDataPreviewProvider::class) postData: PostData
) {
    AppTheme {
        ItemPost(
            item = postData
        )
    }
}