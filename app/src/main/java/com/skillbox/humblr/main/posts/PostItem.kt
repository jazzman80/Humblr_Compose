package com.skillbox.humblr.main.posts

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.Visibility.Companion.Visible
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.main.core.list_subreddit.SubscribeButton
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge
import com.skillbox.humblr.theme.labelMedium

@Composable
fun PostItem(
    item: Post,
    onClick: () -> Unit,
    onSave: (Boolean) -> Unit
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
                    color = MaterialTheme.colorScheme.surface,
                    // shape = MaterialTheme.shapes.medium
                )
                .clickable { onClick() },

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
                text = item.data.title,
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
                modifier = Modifier
                    .constrainAs(subscribeButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                onSubscribe = { onSave(it) },
                initSubscribe = item.data.saved
            )

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(item.data.thumbnail)
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
                text = item.data.selftext,
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
                text = item.data.author,
                style = bodySmall,
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

            Text(
                text = item.data.numComments.toString(),
                style = labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .constrainAs(comments) {
                        baseline.linkTo(author.baseline)
                        end.linkTo(commentsIcon.start, margin = 3.dp)
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_comments),
                contentDescription = "comments",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .constrainAs(commentsIcon) {
                        top.linkTo(comments.top, margin = 2.dp)
                        end.linkTo(endGuide)
                    }
            )

        }
    }

}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewPostItem() {
    AppTheme {
        PostItem(
            item = Post(
                PostData(
                    id = "t5_dbhbsdj",
                    title = "Title",
                    author = "Author",
                    numComments = 225,
                    thumbnail = "self",
                    selftext = "description",
                    saved = false,
                    name = "sdjcnkdckw"
                )
            ),
            onClick = {},
            onSave = {}
        )
    }
}