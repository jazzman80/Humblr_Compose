package com.skillbox.humblr.main.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostListPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ScreenPreview
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

data class PostsScreen(val title: String) : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<PostsViewModel>()
        val posts = viewModel.postFlow(title).collectAsLazyPagingItems()

        PostsScreenContent(
            title = title,
            posts = posts,
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

@Composable
fun PostsScreenContent(
    title: String,
    posts: LazyPagingItems<Post>,
    onRefreshButton: () -> Unit,
    onSave: (Boolean, String) -> Unit
) {
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
                },
            onBack = {}
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
                onRefreshButton()
            },
            onSave = { isSaved, name ->
                onSave(isSaved, name)
            }
        )
    }
}

@ScreenPreview
@Composable
fun PreviewPostsScreen(
    @PreviewParameter(PostListPreviewProvider::class) postList: List<Post>
) {

    val flow = MutableStateFlow(PagingData.from(postList))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        PostsScreenContent(
            title = "Длинные посты",
            posts = lazyPagingItems,
            onRefreshButton = { },
            onSave = { _, _ -> }
        )
    }
}