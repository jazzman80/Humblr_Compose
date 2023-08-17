package com.skillbox.humblr.main.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.titleMedium

@Composable
fun TopBar(
    titleText: String = "",
    onBack: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onBack()
                }
                .padding(all = 14.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            modifier = Modifier
                .padding(all = 5.dp)
                .weight(1f),
            text = titleText,
            color = MaterialTheme.colorScheme.onPrimary,
            style = titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@ElementPreview
@Composable
fun PreviewTopBar() {
    AppTheme {
        TopBar(
            titleText = "Длинные посты"
        )
    }
}