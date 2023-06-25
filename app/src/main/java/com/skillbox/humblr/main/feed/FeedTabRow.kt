package com.skillbox.humblr.main.feed

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium

@Composable
fun FeedTabRow(
    modifier: Modifier,
    onSelect: (Int) -> Unit
) {

    var selectedTab by rememberSaveable {
        mutableStateOf(0)
    }

    TabRow(
        selectedTabIndex = selectedTab,
        modifier = modifier,
        indicator = {},
        divider = {},
        containerColor = Color.Transparent
    ) {
        repeat(2) {
            Tab(
                selected = selectedTab == it,
                onClick = {
                    selectedTab = it
                    onSelect(it)
                }
            ) {

                val selected = selectedTab == it

                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = if (it == 0) stringResource(id = R.string.new_feeds)
                    else stringResource(id = R.string.popular),
                    color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    style = bodyMedium,
                    fontWeight = if (selected) FontWeight.Bold
                    else null
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewFeedTabRow() {
    AppTheme {
        FeedTabRow(modifier = Modifier, {})
    }
}