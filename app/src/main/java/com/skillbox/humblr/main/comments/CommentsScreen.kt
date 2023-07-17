package com.skillbox.humblr.main.comments

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Thing
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

class CommentsScreen(
    private val article: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<CommentsViewModel>()
        val comments = viewModel.getCommentsFlow(article).collectAsLazyPagingItems()

        CommentsScreenContent(
            items = comments,
            onBack = {
                navigator.pop()
            }
        )
    }

}

@Composable
fun CommentsScreenContent(
    items: LazyPagingItems<Thing>,
    onBack: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopBar(
            titleText = stringResource(id = R.string.comments),
            onBack = onBack
        )

        CommentsList(
            pagingItems = items
        )
    }
}

@ElementPreview
@Composable
fun PreviewCommentsScreen(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<Thing>
) {

    val flow = MutableStateFlow(PagingData.from(comments))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        SystemUI {
            MainScreen {
                CommentsScreenContent(
                    lazyPagingItems
                )
            }
        }
    }
}