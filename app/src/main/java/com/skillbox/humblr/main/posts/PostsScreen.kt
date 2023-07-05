package com.skillbox.humblr.main.posts

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.theme.AppTheme

data class PostsScreen(val title: String) : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<PostsViewModel>()
        val posts = viewModel.postFlow(title).collectAsLazyPagingItems()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            val (topBar, list) = createRefs()
            val startGuide = createGuidelineFromStart(0.08F)
            val endGuide = createGuidelineFromEnd(0.08F)

            TopBar(
                titleText = title,
                modifier = Modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            PostList(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(topBar.bottom)
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                pagingItems = posts,
                onRefreshButton = {
                    viewModel.refreshToken()
                    posts.refresh()
                },
                onSave = { isSaved, name ->
                    viewModel.save(isSaved, name)
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
fun PreviewPostsScreen() {
    AppTheme {
        PostsScreen("Posts Screen")
    }
}