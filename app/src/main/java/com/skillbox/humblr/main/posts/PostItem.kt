package com.skillbox.humblr.main.posts

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun PostItem(
    item: Post,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )

    ) {
        val (title, subscribeButton, author) = createRefs()
        val startGuide = createGuidelineFromStart(12.dp)
        val endGuide = createGuidelineFromEnd(12.dp)

        Text(
            text = item.data.title,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                },
            style = labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        //            SubscribeButton(
        //                modifier = Modifier
        //                    .constrainAs(subscribeButton) {
        //                        top.linkTo(parent.top)
        //                        end.linkTo(parent.end)
        //                        bottom.linkTo(parent.bottom)
        //                    }
        //                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp),
        //                onSubscribe = { isSubscribed ->
        //                    onSubscribe(isSubscribed)
        //                },
        //                initSubscribe = item.data.userIsSubscriber
        //            )

        Text(
            text = "author",
            style = bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(title.bottom, margin = 20.dp)
                    start.linkTo(startGuide)
                }
                .padding(bottom = 12.dp)
        )

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
                PostData(id = "t5_dbhbsdj", title = "Title")
            ),
            onClick = {}
        )
    }
}