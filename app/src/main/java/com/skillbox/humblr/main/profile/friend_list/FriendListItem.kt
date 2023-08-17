package com.skillbox.humblr.main.profile.friend_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.LoadingState.ERROR
import com.skillbox.humblr.core.LoadingState.LOADING
import com.skillbox.humblr.core.LoadingState.SUCCESS
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.main.core.Avatar
import com.skillbox.humblr.main.core.FramedText
import com.skillbox.humblr.main.user.UserScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import org.koin.compose.koinInject

@Composable
fun FriendListItem(
    username: String = ""
) {
    val repository = koinInject<Repository>()
    var iconImg by remember { mutableStateOf("") }
    var loadingState by remember { mutableStateOf(LOADING) }

    LaunchedEffect(key1 = true) {
        val result = repository.getUser(username)

        if (result.isSuccessful) {
            iconImg = result.body()?.data?.iconImg!!

            loadingState = SUCCESS
        } else {
            loadingState = ERROR
        }
    }

    FriendListItemContent(
        username = username,
        iconImg = iconImg
    )
}

@Composable
fun FriendListItemContent(
    username: String = "",
    iconImg: String = "",
    loadingState: LoadingState = SUCCESS
) {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier
            .clip(shape = shapes.medium)
            .clickable { navigator?.push(UserScreen(username)) }
            .background(color = colorScheme.surface)
            .height(175.dp)
            .padding(all = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 7.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        when (loadingState) {
            SUCCESS -> {
                Avatar(
                    size = 65.dp,
//                    imageModel = { iconImg }
                    model = iconImg
                )

                FramedText(
                    text = username
                )
            }

            LOADING -> {
                CircularProgressIndicator()
            }

            else -> {
                Text(
                    text = stringResource(
                        id = R.string.error_message
                    )
                )
            }
        }
    }
}

@ElementPreview
@Composable
fun PreviewFriendListItemContent() {
    AppTheme {
        FriendListItemContent(
            username = "Name",
            loadingState = SUCCESS
        )
    }
}