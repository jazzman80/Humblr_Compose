package com.skillbox.humblr.main.single_post

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.main.core.Counter
import com.skillbox.humblr.main.core.LikeButton
import com.skillbox.humblr.main.core.PublishedText
import com.skillbox.humblr.main.core.list_subreddit.SubscribeButton
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge
import java.time.Instant

@Composable
fun SinglePostCard(
    modifier: Modifier,
    item: PostData
) {
    ConstraintLayout(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {

        val (
            title,
            subscribeButton,
            author,
            publicationTime,
            publication,
            image,
            description,
            comments,
            commentsText,
            share,
            likeButton
        ) = createRefs()

        val startGuide = createGuidelineFromStart(14.dp)
        val endGuide = createGuidelineFromEnd(14.dp)

        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = labelLarge,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                }
        )

        SubscribeButton(
            modifier = Modifier
                .constrainAs(subscribeButton) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            onSubscribe = {},
            initSubscribe = false
        )

        Text(
            text = item.author,
            color = MaterialTheme.colorScheme.primary,
            style = labelLarge,
            modifier = Modifier
                .constrainAs(author) {
                    start.linkTo(subscribeButton.end)
                    top.linkTo(subscribeButton.top)
                    bottom.linkTo(subscribeButton.bottom)
                }
        )

        PublishedText(
            publishTime = item.created,
            modifier = Modifier
                .constrainAs(publicationTime) {
                    baseline.linkTo(author.baseline)
                    end.linkTo(endGuide)
                }
        )

        Text(
            text = stringResource(id = R.string.published) + " ",
            color = MaterialTheme.colorScheme.outline,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(publication) {
                    baseline.linkTo(author.baseline)
                    end.linkTo(publicationTime.start)
                }
        )

        SubcomposeAsyncImage(
            model = "https://animalreader.ru/wp-content/uploads/2015/12/seraja-zhaba-ili-obyknovennaja-samaja-krupnaja-sredi-evropejskih-zhab-animal-reader.ru-003.jpg",
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(subscribeButton.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = item.selftext,
            color = MaterialTheme.colorScheme.onSurface,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(image.bottom, margin = 10.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                }
        )

        Counter(
            value = item.numComments,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(comments) {
                    start.linkTo(startGuide)
                    top.linkTo(likeButton.top)
                    bottom.linkTo(likeButton.bottom)
                }
        )

        Text(
            text = " " + pluralStringResource(
                id = R.plurals.comments,
                count = if (item.numComments < 1000) item.numComments else 1000
            ),
            color = MaterialTheme.colorScheme.outline,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(commentsText) {
                    start.linkTo(comments.end)
                    top.linkTo(likeButton.top)
                    bottom.linkTo(likeButton.bottom)
                }
        )

        LikeButton(
            initState = item.saved,
            modifier = Modifier
                .constrainAs(likeButton) {
                    end.linkTo(endGuide)
                    top.linkTo(description.bottom)
                }
        )

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(share) {
                    end.linkTo(likeButton.start)
                    top.linkTo(likeButton.top)
                }
        ) {
            Text(
                text = stringResource(id = R.string.share),
                color = MaterialTheme.colorScheme.outline,
                style = bodySmall,
            )
        }

    }
}

@Preview(
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode"
)
@Composable
fun PreviewSinglePostCard() {
    AppTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black
                )
        ) {
            val card = createRef()

            SinglePostCard(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    },
                item = PostData(
                    title = "Жабы сбежали из болота в соседнем Марьино",
                    author = "КисКисХ2",
                    numComments = 123,
                    selftext = "Все случилось настолько стремительно, что UX/UI-дизайнеры не успели сделать интерфейс.",
                    saved = false,
                    created = Instant.now().epochSecond - 23154
                )
            )
        }

    }
}