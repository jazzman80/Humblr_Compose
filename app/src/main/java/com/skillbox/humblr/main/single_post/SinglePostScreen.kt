package com.skillbox.humblr.main.single_post

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.theme.AppTheme
import java.time.Instant

@Composable
fun SinglePostScreen(
    item: PostData,
    onBack: () -> Unit,
    titleText: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {

        val (topBar, postCard) = createRefs()
        val startGuide = createGuidelineFromStart(12.dp)
        val endGuide = createGuidelineFromEnd(12.dp)

        TopBar(
            onNavIcon = { onBack() },
            titleText = titleText,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        SinglePostCard(
            modifier = Modifier
                .constrainAs(postCard) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                },
            item = item
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
fun PreviewSinglePostScreen() {
    AppTheme {
        SinglePostScreen(
            item = PostData(
                title = "Жабы сбежали из болота в соседнем Марьино",
                author = "КисКисХ2",
                numComments = 123,
                selftext = "Все случилось настолько стремительно, что UX/UI-дизайнеры не успели сделать интерфейс.",
                saved = false,
                created = Instant.now().epochSecond - 23154
            ),
            onBack = {},
            titleText = "Subreddit"
        )
    }
}