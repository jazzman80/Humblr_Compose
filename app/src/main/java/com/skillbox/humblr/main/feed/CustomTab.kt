package com.skillbox.humblr.main.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium

@Composable
fun CustomTab(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    isSelected: Boolean = false
) {

    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.outline

    Text(
        modifier = modifier
            .clickable {
                if (!isSelected) onClick()
            }
            .padding(vertical = 10.dp),
        textAlign = TextAlign.Center,
        text = text,
        color = if (isSelected) selectedColor else unselectedColor,
        style = bodyMedium,
        fontWeight = if (isSelected) FontWeight.Bold else null
    )
}

@ElementPreview
@Composable
fun CustomTabPreview() {
    AppTheme {
        CustomTab(
            modifier = Modifier.width(200.dp),
            text = stringResource(id = R.string.new_feeds)
        )
    }
}