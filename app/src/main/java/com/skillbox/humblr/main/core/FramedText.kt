package com.skillbox.humblr.main.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.titleMedium

@Composable
fun FramedText(
    modifier: Modifier = Modifier,
    text: String = "",
    style: TextStyle = titleMedium,
    color: Color = colorScheme.onBackground,
    backgroundColor: Color = colorScheme.inversePrimary,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 26.dp
) {
    Text(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = backgroundColor)
            .padding(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            ),
        text = text,
        style = style,
        color = color
    )
}

@ElementPreview
@Composable
fun PreviewFramedText() {
    AppTheme {
        FramedText(text = "Text")
    }
}