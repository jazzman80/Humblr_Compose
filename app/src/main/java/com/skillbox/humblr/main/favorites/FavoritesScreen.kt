package com.skillbox.humblr.main.favorites

import MainScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.entity.SubredditListPreviewProvider
import com.skillbox.humblr.main.comments.CommentsList
import com.skillbox.humblr.main.core.list_subreddit.ListSubreddit
import com.skillbox.humblr.main.posts.PostsScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class FavoritesScreen : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val repository = koinInject<Repository>()
        val scope = rememberCoroutineScope()
        val viewModel = koinViewModel<FavoritesViewModel>()
        val subreddits = viewModel.subsFlow.collectAsLazyPagingItems()
        val favoriteComments = viewModel.favoriteComments.collectAsLazyPagingItems()
        var allComments by remember {
            mutableStateOf(listOf<CommentDto>())
        }
        val onNavigate: (String) -> Unit = {
            navigator.push(PostsScreen(it))
        }
        val onSubscribe: (Boolean, String) -> Unit = { isSubscribed, name ->
            scope.launch {
                if (isSubscribed) {
                    repository.unsubscribe(name)
                } else {
                    repository.subscribe(name)
                }
            }
        }

        LaunchedEffect(key1 = true) {
            allComments = repository.getAllComments()
        }

        FavoritesScreenContent(
            subreddits = subreddits,
            favoriteComments = favoriteComments,
            savedComments = allComments,
            onNavigate = onNavigate,
            onSubscribe = onSubscribe
        )
    }
}

@Composable
fun FavoritesScreenContent(
    subreddits: LazyPagingItems<Subreddit>,
    favoriteComments: LazyPagingItems<CommentDto>,
    savedComments: List<CommentDto>,
    onNavigate: (String) -> Unit = {},
    onSubscribe: (Boolean, String) -> Unit = { _, _ -> }
) {

    var isSubsSelected by remember { mutableStateOf(true) }
    var isAllSelected by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FavoritesSelector(
            firstItemTitle = stringResource(id = R.string.subreddits),
            secondItemTitle = stringResource(id = R.string.comments),
            onFirstItemSelected = {
                isSubsSelected = true
            },
            onSecondItemSelected = {
                isSubsSelected = false
            }
        )

        FavoritesSelector(
            firstItemTitle = stringResource(id = R.string.all),
            secondItemTitle = stringResource(id = R.string.saved),
            isActive = !isSubsSelected,
            onFirstItemSelected = {
                isAllSelected = true
            },
            onSecondItemSelected = {
                isAllSelected = false
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        if (isSubsSelected) {
            ListSubreddit(
                pagingItems = subreddits,
                onNavigate = onNavigate,
                onSubscribe = onSubscribe
            )
        } else if (isAllSelected) {
            CommentsList(
                lazyComments = favoriteComments
            )
        } else {
            CommentsList(
                comments = savedComments
            )
        }
    }
}

@ElementPreview
@Composable
fun PreviewFavoritesScreen(
    @PreviewParameter(SubredditListPreviewProvider::class) subsList: List<Subreddit>
) {

    val flow = MutableStateFlow(PagingData.from(subsList))
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    AppTheme {
        SystemUI {
            MainScreen {
//                FavoritesScreenContent(
////                    subreddits = lazyPagingItems
//                )
            }
        }
    }
}