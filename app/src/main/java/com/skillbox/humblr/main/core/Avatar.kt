package com.skillbox.humblr.main.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    model: String = ""
//    imageModel: () -> Any = {}
) {
//    CoilImage(
//        modifier = modifier
//            .clip(CircleShape)
//            .background(color = colorScheme.outline)
//            .size(size),
//        imageModel = imageModel,
//        previewPlaceholder = R.drawable.sample_avatar,
//        component = rememberImageComponent {
//            +ThumbnailPlugin()
//            +PlaceholderPlugin.Loading(
//                painterResource(id = R.drawable.avatar_placeholder)
//            )
//        },
//    )

//    AsyncImage(
//        model = model,
//        contentDescription = "",
//        modifier = modifier
//            .clip(CircleShape)
//            .background(color = colorScheme.outline)
//            .size(size)
//    )

    GlideImage(
        model = model,
        contentDescription = "",
        modifier = modifier
            .clip(CircleShape)
            .background(color = colorScheme.outline)
            .size(size)
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