package com.skillbox.humblr.main.single_post

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.entity.PostData
import com.skillbox.humblr.entity.PostDataPreviewProvider
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

data class SinglePostScreen(
    val name: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<SinglePostViewModel>()
        val item by viewModel.postData.observeAsState()
        val context = LocalContext.current

        viewModel.getPost(name)

        SinglePostScreenContent(
            item = item ?: PostData(),
            onBack = {
                navigator.pop()
            },
            onLike = { isLiked, name ->
                viewModel.save(isLiked, name)
            },
            onShare = {
                viewModel.share(context, item?.permalink ?: "")
            }
        )
    }
}

@Composable
fun SinglePostScreenContent(
    item: PostData,
    onBack: () -> Unit = {},
    onLike: (Boolean, String) -> Unit = { _, _ -> },
    onShare: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopBar(
            titleText = item.subreddit,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            SinglePostCard(
                item = item,
                onLike = onLike,
                onShare = onShare
            )
        }
    }
}

@ElementPreview
@Composable
fun PreviewSinglePostScreen(
    @PreviewParameter(PostDataPreviewProvider::class) postData: PostData
) {
    AppTheme {
        SystemUI {
            MainScreen {
                SinglePostScreenContent(
                    item = postData
                )
            }
        }

    }
}