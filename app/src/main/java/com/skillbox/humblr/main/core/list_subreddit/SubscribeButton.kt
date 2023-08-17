package com.skillbox.humblr.main.core.list_subreddit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun SubscribeButton(
    onSubscribe: (Boolean) -> Unit,
    initSubscribe: Boolean
) {

    var isSubscribed by remember(key1 = initSubscribe) {
        mutableStateOf(initSubscribe)
    }
    val onClick = remember {
        {
            onSubscribe(isSubscribed)
            isSubscribed = !isSubscribed
        }
    }

    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .padding(all = 10.dp),
        painter = painterResource(
            id = if (isSubscribed) R.drawable.ic_unsubscribe
            else R.drawable.ic_subscribe
        ),
        tint = if (isSubscribed) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "subscribe button"
    )

}

@ElementPreview
@Composable
fun PreviewSubscribeButton() {
    AppTheme {
        Row(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            SubscribeButton(
                onSubscribe = {},
                initSubscribe = false
            )
            SubscribeButton(
                onSubscribe = {},
                initSubscribe = true
            )
        }
    }
}