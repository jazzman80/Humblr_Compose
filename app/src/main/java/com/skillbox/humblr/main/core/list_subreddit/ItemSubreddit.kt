package com.skillbox.humblr.main.core.list_subreddit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.entity.SubredditData
import com.skillbox.humblr.entity.SubredditDataPreviewProvider
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun ItemSubreddit(
    item: SubredditData,
    //onSubscribe: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = item.title,
                style = labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            SubscribeButton(
                onSubscribe = {
                    //TODO onSubscribe(isSubscribed)
                },
                initSubscribe = item.userIsSubscriber
            )
        }

        if (item.description.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(end = 32.dp),
                text = item.description,
                style = bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}

@ElementPreview
@Composable
fun PreviewItemSubreddit(
    @PreviewParameter(SubredditDataPreviewProvider::class) subData: SubredditData
) {
    AppTheme {
        ItemSubreddit(
            subData
        )
    }
}