package com.skillbox.humblr.main.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.labelLarge

@Composable
fun FavoritesSelectorItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {

    val textSelected = MaterialTheme.colorScheme.onPrimary
    val textUnselected = MaterialTheme.colorScheme.outline
    val backgroundSelected = MaterialTheme.colorScheme.primary
    val backgroundUnselected = Color.Transparent

    Text(
        modifier = modifier
            .clickable { onClick() }
            .clip(shape = if (isSelected) CircleShape else RectangleShape)
            .background(
                color = if (isSelected) backgroundSelected else backgroundUnselected
            )
            .padding(all = 8.dp),
        text = title,
        textAlign = TextAlign.Center,
        color = if (isSelected) textSelected else textUnselected,
        style = labelLarge
    )
}

@ElementPreview
@Composable
fun PreviewFavoritesSelectorItem() {
    AppTheme {
        FavoritesSelectorItem(
            title = "Item name",
            isSelected = false
        )
    }
}