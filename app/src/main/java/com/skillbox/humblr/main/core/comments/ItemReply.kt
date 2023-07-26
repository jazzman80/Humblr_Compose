package com.skillbox.humblr.main.core.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.CommentDto
import com.skillbox.humblr.entity.CommentPreviewProvider
import com.skillbox.humblr.main.core.PublishedText
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun ItemReply(
    item: CommentDto = CommentDto(),
    onDownload: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(
                start = 32.dp,
                top = 12.dp,
                end = 12.dp,
                bottom = 12.dp
            )
    ) {

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (LocalInspectionMode.current) {

                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp),
                    painter = painterResource(
                        id = R.drawable.sample_avatar
                    ),
                    contentDescription = null
                )

            } else {

                AsyncImage(
                    model = item.avatar,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp),
                    contentDescription = null
                )
            }

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .clickable {
                    onDownload()
                }
                .padding(
                    start = 30.dp,
                    top = 6.dp
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dowload),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )

            Text(
                text = stringResource(
                    id = R.string.download,
                ),
                style = bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }

    }
}

@ElementPreview
@Composable
fun ItemReplyPreview(
    @PreviewParameter(CommentPreviewProvider::class) comment: CommentDto
) {
    AppTheme {
        ItemReply(
            item = comment
        )
    }
}