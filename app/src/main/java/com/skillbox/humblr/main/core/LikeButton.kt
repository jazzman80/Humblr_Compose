package com.skillbox.humblr.main.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun LikeButton(
    initState: Boolean,
    onClick: (Boolean) -> Unit = {}
) {

    var isLiked by rememberSaveable {
        mutableStateOf(initState)
    }

    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick(isLiked)
                isLiked = !isLiked
            }
            .padding(
                all = 6.dp
            ),
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

@ElementPreview
@Composable
fun PreviewLikeButton() {
    AppTheme {

        LikeButton(
            initState = false
        )

    }
}