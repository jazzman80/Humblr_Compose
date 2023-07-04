package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium

@Composable
fun Counter(
    value: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline,
    style: TextStyle = bodyMedium
) {

    val textValue: String = if (value < 1000) {
        value.toString()
    } else {
        val kValue = value.toDouble() / 1000

        val formatValue: String = if (value % 1000 < 100) {
            String.format("%.0f", kValue)
        } else {
            String.format("%.1f", kValue)
        }
        formatValue + "K"
    }

    Text(
        text = textValue,
        color = color,
        style = style,
        modifier = modifier
    )
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewCounter() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        )
        {
            Counter(
                value = 471
            )
        }
    }
}