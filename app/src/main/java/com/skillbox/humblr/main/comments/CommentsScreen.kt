package com.skillbox.humblr.main.comments

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

class CommentsScreen(
    private val article: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<CommentsViewModel>()
        //val comments = viewModel.getCommentsFlow(article).collectAsLazyPagingItems()
        val comments by viewModel.comments.observeAsState()

        viewModel.getComments(article)

        CommentsScreenContent(
            comments = comments,
            onBack = {
                navigator.pop()
            }
        )
    }

}

@Composable
fun CommentsScreenContent(
    //items: LazyPagingItems<Thing>,
    comments: List<CommentDto>?,
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
            comments = comments
        )
    }
}

@ElementPreview
@Composable
fun PreviewCommentsScreen(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<CommentDto>
) {

//    val flow = MutableStateFlow(PagingData.from(comments))
//    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        SystemUI {
            MainScreen {
                CommentsScreenContent(
                    comments = comments
                )
            }
        }
    }
}