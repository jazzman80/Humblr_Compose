package com.skillbox.humblr.main.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import com.skydoves.landscapist.placeholder.thumbnail.ThumbnailPlugin

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    imageModel: () -> Any = {}
) {
    CoilImage(
        modifier = modifier
            .clip(CircleShape)
            .background(color = colorScheme.outline)
            .size(size),
        imageModel = imageModel,
        previewPlaceholder = R.drawable.sample_avatar,
        component = rememberImageComponent {
            +ThumbnailPlugin()
            +PlaceholderPlugin.Loading(
                painterResource(id = R.drawable.avatar_placeholder)
            )
        },
    )
}

@ElementPreview
@Composable
fun PreviewAvatar() {
    AppTheme {
        Avatar(
            size = 100.dp
        )
    }
}