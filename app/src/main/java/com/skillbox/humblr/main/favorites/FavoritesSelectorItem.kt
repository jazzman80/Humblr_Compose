package com.skillbox.humblr.main.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.DISABLED
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.SELECTED
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.UNSELECTED
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.labelLarge

@Composable
fun FavoritesSelectorItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    state: FavoritesSelectorState = UNSELECTED
) {

    val textColor: Color
    val backgroundColor: Color


    when (state) {
        UNSELECTED -> {
            textColor = colorScheme.outline
            backgroundColor = Color.Transparent
        }

        SELECTED -> {
            textColor = colorScheme.onPrimary
            backgroundColor = colorScheme.primary
        }

        DISABLED -> {
            textColor = colorScheme.outlineVariant
            backgroundColor = colorScheme.outline
        }
    }

    val defaultModifier = modifier
        .clip(shape = CircleShape)
//        .background(
//            color = backgroundColor
//        )
        .padding(all = 8.dp)
    val clickableModifier = defaultModifier.clickable { onClick() }

    Text(
        modifier = if (state == UNSELECTED) clickableModifier else defaultModifier,
        text = title,
        textAlign = TextAlign.Center,
        color = textColor,
        style = labelLarge
    )
}

@ElementPreview
@Composable
fun PreviewFavoritesSelectorItem() {
    AppTheme {
        FavoritesSelectorItem(
            title = "Item name",
            isSelected = false,
            state = DISABLED
        )
    }
}