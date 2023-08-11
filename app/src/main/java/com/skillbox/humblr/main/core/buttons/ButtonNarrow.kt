package com.skillbox.humblr.main.core.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.labelLarge

@Composable
fun ButtonNarrow(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: Painter? = null,
    backgroundColor: Color = colorScheme.primary,
    textColor: Color = colorScheme.onPrimary,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(shape = CircleShape)
            .clickable { onClick() }
            .background(color = backgroundColor)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textColor,
            style = labelLarge
        )
        if (icon != null) {
            Icon(
                modifier = Modifier
                    .size(size = 14.dp),
                painter = icon,
                contentDescription = null,
                tint = textColor
            )
        }
    }
}

@ElementPreview
@Composable
fun PreviewButtonNarrow() {
    AppTheme {
        ButtonNarrow()
    }
}