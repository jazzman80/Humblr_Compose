package com.skillbox.humblr.main.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun NavBar(
    selectedTabId: Int = 0,
    onTabSelect: (Int) -> Unit = {}
) {
    val icons = listOf(
        R.drawable.ic_feed,
        R.drawable.ic_favorites,
        R.drawable.ic_profile
    )

    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(3) { index ->
            NavBarItem(
                modifier = Modifier
                    .weight(1f),
                icon = icons[index],
                id = index,
                selectedId = selectedTabId,
                onClick = { onTabSelect(index) }
            )
        }

    }
}

@ElementPreview
@Composable
fun PreviewNavBar() {
    AppTheme {
        NavBar()
    }
}