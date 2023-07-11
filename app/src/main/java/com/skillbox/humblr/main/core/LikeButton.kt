package com.skillbox.humblr.main.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun LikeButton(
    initState: Boolean
) {

    var isLiked by remember { mutableStateOf(initState) }

    IconButton(
        onClick = {
            isLiked = !isLiked
        }
    ) {

        Icon(
            painter = if (isLiked) painterResource(id = R.drawable.ic_liked)
            else painterResource(id = R.drawable.ic_unliked),
            contentDescription = "like button",
            tint = animateColorAsState(
                targetValue = if (isLiked) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.outline,
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                )
            ).value
        )

    }
}

@ElementPreview
@Composable
fun PreviewLikeButton() {
    AppTheme {

        LikeButton(
            initState = false
        )

    }
}