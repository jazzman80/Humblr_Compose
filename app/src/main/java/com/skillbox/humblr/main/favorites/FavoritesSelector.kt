package com.skillbox.humblr.main.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun FavoritesSelector(
    firstItemTitle: String = "",
    secondItemTitle: String = ""
) {

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    Row(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        FavoritesSelectorItem(
            modifier = Modifier.weight(1f),
            title = firstItemTitle,
            isSelected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )

        FavoritesSelectorItem(
            modifier = Modifier.weight(1f),
            title = secondItemTitle,
            isSelected = selectedItem == 1,
            onClick = { selectedItem = 1 }
        )
    }
}

@ElementPreview
@Composable
fun PreviewFavoritesSelector() {
    AppTheme {
        FavoritesSelector(
            firstItemTitle = "first",
            secondItemTitle = "second"
        )
    }
}