package com.skillbox.humblr.main.posts

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.skillbox.humblr.theme.labelLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItem(
    item: Post,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (title, subscribeButton) = createRefs()

            Text(
                text = item.data.title,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(subscribeButton.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 12.dp, start = 12.dp, bottom = 12.dp),
                style = labelLarge
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
                PostData(id = "t5_dbhbsdj", title = "Title")
            ),
            onClick = {}
        )
    }
}