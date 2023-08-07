package com.skillbox.humblr.main.favorites

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.DISABLED
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.SELECTED
import com.skillbox.humblr.main.favorites.FavoritesSelectorState.UNSELECTED
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun FavoritesSelector(
    firstItemTitle: String = "",
    secondItemTitle: String = "",
    isActive: Boolean = true,
    onFirstItemSelected: () -> Unit = {},
    onSecondItemSelected: () -> Unit = {}
) {

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    val itemsState = remember(key1 = isActive) {
        mutableStateListOf(
            if (selectedItem == 0) SELECTED else UNSELECTED,
            if (selectedItem == 1) SELECTED else UNSELECTED
        )
    }

    if (!isActive) {
        itemsState[0] = DISABLED
        itemsState[1] = DISABLED
    }

    var height by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0.dp) }
    var endOffset by remember { mutableStateOf(IntOffset.Zero) }

//    val offset by animateDpAsState(targetValue = if (selectedItem == 0) 0.dp else width / 2)
    val offset by animateIntOffsetAsState(
        targetValue = if (selectedItem == 0) IntOffset.Zero else endOffset
    )
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = colorScheme.surface)
            .fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier.matchParentSize(),
            onDraw = {

                val xOffset = this.size.width.toInt() / 2
                endOffset = IntOffset(xOffset, 0)

                height = this.size.toDpSize().height
                width = this.size.toDpSize().width
            }
        )

        Box(
            modifier = Modifier
                .offset {
                    offset
                }
                .clip(shape = CircleShape)
                .background(
                    color = if (isActive) colorScheme.primary else colorScheme.outline
                )
                .width(width = width / 2)
                .height(height = height)
        ) {

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            FavoritesSelectorItem(
                modifier = Modifier.weight(1f),
                title = firstItemTitle,
                onClick = {
                    itemsState[0] = SELECTED
                    itemsState[1] = UNSELECTED
                    selectedItem = 0
                    onFirstItemSelected()
                },
                state = itemsState[0]
            )

            FavoritesSelectorItem(
                modifier = Modifier.weight(1f),
                title = secondItemTitle,
                onClick = {
                    itemsState[0] = UNSELECTED
                    itemsState[1] = SELECTED
                    selectedItem = 1
                    onSecondItemSelected()
                },
                state = itemsState[1]
            )

        }
    }

}

@ElementPreview
@Composable
fun PreviewFavoritesSelector() {
    AppTheme {
        FavoritesSelector(
            firstItemTitle = "first",
            secondItemTitle = "second",
            isActive = false
        )
    }
}