package com.skillbox.humblr.main.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
    onSave: (Boolean) -> Unit,
    navigateToPost: () -> Unit
) {

    var imageVisibility by remember {
        mutableStateOf(Visible)
    }

    Box(
        modifier = Modifier.clip(MaterialTheme.shapes.medium)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .clickable { navigateToPost() },

            ) {
            val (
                title,
                subscribeButton,
                author,
                comments,
                commentsIcon,
                thumbnail,
                description
            ) = createRefs()
            val startGuide = createGuidelineFromStart(12.dp)
            val endGuide = createGuidelineFromEnd(12.dp)

            Text(
                text = item.title,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(startGuide)
                        end.linkTo(subscribeButton.start)
                        width = Dimension.fillToConstraints
                    },
                style = labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            SubscribeButton(
                onSubscribe = { onSave(it) },
                initSubscribe = item.saved
            )

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(item.thumbnail)
                    .placeholder(R.drawable.image_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(thumbnail) {
                        top.linkTo(title.bottom, margin = 20.dp)
                        start.linkTo(startGuide)
                        visibility = imageVisibility
                        width = Dimension.value(100.dp)
                        height = Dimension.value(100.dp)
                    },
                onError = { imageVisibility = Gone }
            )

            Text(
                text = item.selftext,
                style = bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(title.bottom, margin = 16.dp)
                        start.linkTo(thumbnail.end, margin = 10.dp)
                        end.linkTo(endGuide)
                        width = Dimension.fillToConstraints
                    }
            )

            val bottomBarrier = createBottomBarrier(thumbnail, description)

            Text(
                text = item.author,
                style = bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .constrainAs(author) {
                        top.linkTo(bottomBarrier, margin = 10.dp)
                        start.linkTo(startGuide)
                        end.linkTo(comments.start, margin = 20.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(bottom = 12.dp)
            )

            Counter(
                value = item.numComments,
                modifier = Modifier
                    .constrainAs(comments) {
                        baseline.linkTo(author.baseline)
                        end.linkTo(commentsIcon.start, margin = 3.dp)
                    },
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_comments),
                contentDescription = "comments",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .constrainAs(commentsIcon) {
                        top.linkTo(comments.top, margin = 3.dp)
                        bottom.linkTo(comments.bottom)
                        end.linkTo(endGuide)
                    }
            )

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
            item = postData,
            onSave = {},
            navigateToPost = {}
        )
    }
}