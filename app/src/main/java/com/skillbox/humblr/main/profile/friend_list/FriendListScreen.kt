package com.skillbox.humblr.main.profile.friend_list

import MainScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.androidx.AndroidScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

class FriendListScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        FriendListContent()
    }
}

@Composable
fun FriendListContent() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Friend List",
        textAlign = TextAlign.Center
    )
}

@ElementPreview
@Composable
fun PreviewFriendListContent() {
    AppTheme {
        SystemUI {
            MainScreen {
                FriendListContent()
            }
        }
    }
}