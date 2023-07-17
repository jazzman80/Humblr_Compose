package com.skillbox.humblr.main.core.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.CommentPreviewProvider
import com.skillbox.humblr.main.core.PublishedText
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun ItemComment(
    item: CommentDto = CommentDto(),
    avatar: String? = null
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = 12.dp)
    ) {

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatar,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp),
//                painter = painterResource(
//                    id = R.drawable.sample_avatar
//                ),
                contentDescription = null,

                )

            item.author?.let {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = it,
                    style = labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


            item.created?.let {
                PublishedText(
                    publishTime = it,
                )
            }


        }

        item.body?.let {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {

                Divider(
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .width(1.dp)
                        .fillMaxHeight()
                )

                Text(
                    text = it,
                    style = bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    }
}

@ElementPreview
@Composable
fun ItemCommentPreview(
    @PreviewParameter(CommentPreviewProvider::class) comment: CommentDto
) {
    AppTheme {
        ItemComment(
            item = comment
        )
    }
}