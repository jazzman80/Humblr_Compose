package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import java.time.Instant

@Composable
fun PublishedText(
    publishTime: Long,
    modifier: Modifier = Modifier
) {

    val diff = Instant.now().epochSecond - publishTime
    val text: String
    val value: Long

    if (diff <= 60) {
        text = stringResource(id = R.string.now)
    } else if (diff <= 3600) {
        value = diff / 60
        text = pluralStringResource(
            id = R.plurals.minutesAgo,
            count = value.toInt(),
            value
        )
    } else if (diff <= 86400) {
        value = diff / 3600
        text = pluralStringResource(
            id = R.plurals.hoursAgo,
            count = value.toInt(),
            value
        )
    } else if (diff <= 2592000) {
        value = diff / 86400
        text = pluralStringResource(
            id = R.plurals.daysAgo,
            count = value.toInt(),
            value
        )
    } else if (diff <= 31356000) {
        value = diff / 2592000
        text = pluralStringResource(
            id = R.plurals.monthsAgo,
            count = value.toInt(),
            value
        )
    } else {
        value = diff / 31356000
        text = pluralStringResource(
            id = R.plurals.yearsAgo,
            count = value.toInt(),
            value
        )
    }

    Text(
        text = text,
        color = MaterialTheme.colorScheme.outline,
        style = bodySmall,
        modifier = modifier
    )
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewPublishedText() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            PublishedText(
                publishTime = Instant.now().epochSecond - 220005874
            )
        }
    }
}