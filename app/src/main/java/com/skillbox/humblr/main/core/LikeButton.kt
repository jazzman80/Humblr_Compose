package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme

@Composable
fun LikeButton(
    initState: Boolean,
    modifier: Modifier
) {

    var isLiked by rememberSaveable { mutableStateOf(initState) }

    IconButton(
        onClick = {
            isLiked = !isLiked
        },
        modifier = modifier
    ) {

        Icon(
            painter = if (isLiked) painterResource(id = R.drawable.ic_liked)
            else painterResource(id = R.drawable.ic_unliked),
            contentDescription = "like button",
            tint = if (isLiked) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.outline
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
fun PreviewLikeButton() {
    AppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LikeButton(
                modifier = Modifier,
                initState = false
            )
        }
    }
}