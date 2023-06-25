package com.skillbox.humblr.main.core.list_subreddit

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme

@Composable
fun SubscribeButton(
    modifier: Modifier,
    onSubscribe: (Boolean) -> Unit,
    initSubscribe: Boolean
) {

    var isSubscribed by rememberSaveable {
        mutableStateOf(initSubscribe)
    }

    IconButton(
        onClick = {
            onSubscribe(isSubscribed)
            isSubscribed = !isSubscribed
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(
                id = if (isSubscribed) R.drawable.ic_unsubscribe
                else R.drawable.ic_subscribe
            ),
            tint = if (isSubscribed) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.primaryContainer,
            contentDescription = "subscribe button"
        )
    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewSubscribeButton() {
    AppTheme {
        SubscribeButton(Modifier, {}, false)
    }
}