package com.skillbox.humblr.main.feed

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun FeedTabs(
    selectedTabId: Int = 0,
    onTabSelect: (Int) -> Unit = {}
) {

    val tabTitle = listOf(
        R.string.new_feeds,
        R.string.popular
    )

    Row {

        repeat(2) { index ->

            val isSelected = index == selectedTabId

            CustomTab(
                modifier = Modifier.weight(1f),
                text = stringResource(id = tabTitle[index]),
                isSelected = isSelected,
                onClick = { onTabSelect(index) }
            )
        }
    }
}

@ElementPreview
@Composable
fun PreviewFeedTabRow() {
    AppTheme {

        var selectedTabId by remember { mutableStateOf(0) }

        FeedTabs(
            selectedTabId = selectedTabId,
            onTabSelect = { selectedTabId = it }
        )
    }
}