package com.skillbox.humblr.main.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall

@Composable
fun DownloadButton(
    onDownload: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onDownload()
            }
            .padding(
                all = 6.dp
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

@ElementPreview
@Composable
fun PreviewDownloadButton() {
    AppTheme {
        DownloadButton()
    }
}