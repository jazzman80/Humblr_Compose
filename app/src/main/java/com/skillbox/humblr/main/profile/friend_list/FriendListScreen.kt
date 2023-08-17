package com.skillbox.humblr.main.profile.friend_list

import MainScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.LoadingState.ERROR
import com.skillbox.humblr.core.LoadingState.LOADING
import com.skillbox.humblr.core.LoadingState.SUCCESS
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.fake_data.usernameTestList
import com.skillbox.humblr.main.core.LoadIndicator
import com.skillbox.humblr.main.core.TopBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import org.koin.compose.koinInject

class FriendListScreen : AndroidScreen() {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val repository = koinInject<Repository>()
        var friendList by remember {
            mutableStateOf<List<String>>(listOf())
        }
        var loadingState by remember {
            mutableStateOf(LOADING)
        }

        LaunchedEffect(key1 = true) {

            val result = repository.getFriends()

            if (result.isSuccessful) {

                val usernames = mutableListOf<String>()

                for (user in result.body()!!.data.children) {
                    usernames.add(user.name)
                }

                friendList = usernames
                loadingState = SUCCESS

            } else {
                loadingState = ERROR
            }
        }

        FriendListScreenContent(
            friendList = friendList,
            loadingState = loadingState,
            onBack = {
                navigator.pop()
            }
        )
    }
}

@Composable
fun FriendListScreenContent(
    friendList: List<String>,
    loadingState: LoadingState = LOADING,
    onBack: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            titleText = stringResource(id = R.string.friend_list),
            onBack = onBack
        )

        if (loadingState == SUCCESS) {
            FriendList(
                items = friendList
            )
        } else {
            LoadIndicator(
                loadingState = loadingState
            )
        }
    }
}

@ElementPreview
@Composable
fun PreviewFriendListContent() {
    AppTheme {

        SystemUI {
            MainScreen {
                FriendListScreenContent(
                    friendList = usernameTestList,
                    loadingState = SUCCESS
                )
            }
        }
    }
}