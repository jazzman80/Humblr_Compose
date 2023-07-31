package com.skillbox.humblr.main.comments

import MainScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.fake_data.CommentListPreviewProvider
import com.skillbox.humblr.main.core.LoadIndicator
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class CommentsScreen(
    private val article: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<CommentsViewModel>()
        val comments by viewModel.comments.observeAsState()
        val loadingState by viewModel.loadingState.observeAsState()

        LaunchedEffect(true) {
            viewModel.getComments(article)
        }

        val scope = rememberCoroutineScope()

        CommentsScreenContent(
            comments = comments,
            onBack = {
                navigator.pop()
            },
            onDownload = {
                scope.launch {
                    viewModel.download(it)
                }
            },
            loadingState = loadingState,
            onRefresh = {
                scope.launch {
                    viewModel.getComments(article)
                }
            }
        )
    }

}

@Composable
fun CommentsScreenContent(
    comments: List<CommentDto>?,
    onBack: () -> Unit = {},
    onDownload: (CommentDto) -> Unit = {},
    loadingState: LoadingState? = LoadingState.LOADING,
    onRefresh: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopBar(
            titleText = stringResource(id = R.string.comments),
            onBack = onBack
        )

        if (loadingState == LoadingState.SUCCESS) {
            CommentsList(
                comments = comments,
                onDownload = onDownload
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LoadIndicator(
                    loadingState = loadingState,
                    onRefresh = onRefresh
                )
            }
        }

    }
}

@ElementPreview
@Composable
fun PreviewCommentsScreen(
    @PreviewParameter(CommentListPreviewProvider::class) comments: List<CommentDto>
) {

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