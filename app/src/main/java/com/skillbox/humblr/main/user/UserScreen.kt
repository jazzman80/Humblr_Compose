package com.skillbox.humblr.main.user

import MainScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.LoadingState.ERROR
import com.skillbox.humblr.core.LoadingState.LOADING
import com.skillbox.humblr.core.LoadingState.SUCCESS
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.UserDto
import com.skillbox.humblr.main.comments.CommentsList
import com.skillbox.humblr.main.core.LoadIndicator
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.main.core.buttons.ButtonNarrow
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.titleLarge
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import com.skydoves.landscapist.placeholder.thumbnail.ThumbnailPlugin
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

data class UserScreen(
    private val username: String
) : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val repository = koinInject<Repository>()
        val scope = rememberCoroutineScope()
        val userComments = repository.getUserComments(username).flow.collectAsLazyPagingItems()
        var loadingState by remember {
            mutableStateOf(LOADING)
        }

        var user by remember {
            mutableStateOf(
                UserDto()
            )
        }
        var isSubscribed by remember(key1 = user) {
            mutableStateOf(user.isFriend)
        }

        LaunchedEffect(key1 = true) {
            val result = repository.getUser(username)

            if (result.isSuccessful) {
                user = result.body()?.data?.toUserDto()!!
                loadingState = SUCCESS
            } else {
                loadingState = ERROR
            }
        }

        UserScreenContent(user = user, subscribe = {
            isSubscribed = true
            scope.launch {
                repository.becomeFriends(username)
            }

        }, unsubscribe = {
            isSubscribed = false
            scope.launch {
                repository.stopBeingFriends(username)
            }
        }, isSubscribed = isSubscribed, userComments = userComments, loadingState = loadingState,
            onBack = {
                navigator.pop()
            }
        )
    }

}

@Composable
fun UserScreenContent(
    user: UserDto? = null,
    subscribe: () -> Unit = {},
    unsubscribe: () -> Unit = {},
    showAll: () -> Unit = {},
    isSubscribed: Boolean = false,
    userComments: LazyPagingItems<CommentDto>? = null,
    loadingState: LoadingState = LOADING,
    onBack: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TopBar(
            titleText = user?.name ?: "",
            onBack = onBack
        )

        if (loadingState == SUCCESS) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clip(shape = shapes.medium)
                    .background(color = colorScheme.surface)
                    .padding(all = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                CoilImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorScheme.outline)
                        .size(100.dp),
                    imageModel = { user?.iconImg },
                    previewPlaceholder = R.drawable.sample_avatar,
                    component = rememberImageComponent {
                        +ThumbnailPlugin()
                        +PlaceholderPlugin.Loading(
                            painterResource(id = R.drawable.avatar_placeholder)
                        )
                    },
                )

                Column {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .clip(CircleShape)
                            .background(color = colorScheme.inversePrimary)
                            .padding(
                                horizontal = 22.dp, vertical = 2.dp
                            ),
                        text = user?.name ?: "",
                        style = titleLarge,
                        color = colorScheme.onBackground
                    )
                }

            }

            if (!isSubscribed) {
                ButtonNarrow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.subscribe),
                    icon = painterResource(id = R.drawable.ic_subscribe),
                    onClick = subscribe
                )
            } else {
                ButtonNarrow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.in_friend_list),
                    icon = painterResource(id = R.drawable.ic_unsubscribe),
                    backgroundColor = colorScheme.secondary,
                    textColor = colorScheme.onSecondary,
                    onClick = unsubscribe
                )
            }

            CommentsList(
                lazyComments = userComments
            )
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadIndicator(
                    loadingState = loadingState
                )
            }
        }

    }

}

@ElementPreview
@Composable
fun PreviewUserScreen() {
    AppTheme {
        SystemUI {
            MainScreen {
                UserScreenContent(
                    loadingState = SUCCESS
                )
            }
        }
    }
}
