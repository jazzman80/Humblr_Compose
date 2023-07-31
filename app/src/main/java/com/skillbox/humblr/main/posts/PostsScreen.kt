package com.skillbox.humblr.main.posts

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.entity.Post
import com.skillbox.humblr.entity.PostListPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.main.single_post.SinglePostScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

data class PostsScreen(val title: String) : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<PostsViewModel>()
        val posts = viewModel.postFlow(URLEncoder.encode(title)).collectAsLazyPagingItems()

        PostsScreenContent(
            title = title,
            posts = posts,
            onRefreshButton = {
                viewModel.refreshToken()
                posts.refresh()
            },
            onSave = { isSaved, name ->
                viewModel.save(isSaved, name)
            },
            onBack = {
                navigator.pop()
            },
            onNavigate = {
                navigator.push(SinglePostScreen(it))
            }
        )
    }
}

@Composable
fun PostsScreenContent(
    title: String,
    posts: LazyPagingItems<Post>,
    onRefreshButton: () -> Unit = {},
    onSave: (Boolean, String) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopBar(
            titleText = title,
            onBack = onBack
        )

        PostList(
            pagingItems = posts,
            onRefreshButton = onRefreshButton,
            onSave = onSave,
            onNavigate = onNavigate
        )
    }
}

@ElementPreview
@Composable
fun PreviewPostsScreen(
    @PreviewParameter(PostListPreviewProvider::class) postList: List<Post>
) {

    val flow = MutableStateFlow(PagingData.from(postList))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        SystemUI {
            MainScreen {
                PostsScreenContent(
                    title = "Длинные посты",
                    posts = lazyPagingItems
                )
            }
        }
    }
}