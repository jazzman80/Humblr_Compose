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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.skillbox.humblr.main.core.LikeButton
import com.skillbox.humblr.main.core.list_subreddit.SubscribeButton
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun SinglePostCard(
    modifier: Modifier
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
            publication,
            image,
            description,
            comments,
            share,
            likeButton
        ) = createRefs()

        val startGuide = createGuidelineFromStart(14.dp)
        val endGuide = createGuidelineFromEnd(14.dp)

        Text(
            text = "Жабы сбежали из болота в соседнем Марьино",
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
            text = "КисКисХ2",
            color = MaterialTheme.colorScheme.primary,
            style = labelLarge,
            modifier = Modifier
                .constrainAs(author) {
                    start.linkTo(subscribeButton.end)
                    top.linkTo(subscribeButton.top)
                    bottom.linkTo(subscribeButton.bottom)
                }
        )

        Text(
            text = "опубликовано 3 часа назад",
            color = MaterialTheme.colorScheme.outline,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(publication) {
                    baseline.linkTo(author.baseline)
                    end.linkTo(endGuide)
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
            text = "Все случилось настолько стремительно, что UX/UI-дизайнеры не успели сделать интерфейс.",
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

        Text(
            text = "22 комментария",
            color = MaterialTheme.colorScheme.outline,
            style = bodySmall,
            modifier = Modifier
                .constrainAs(comments) {
                    start.linkTo(startGuide)
                    top.linkTo(likeButton.top)
                    bottom.linkTo(likeButton.bottom)
                }
        )

        LikeButton(
            initState = false,
            initLikes = 100,
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
                text = "Share",
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
                    }
            )
        }

    }
}