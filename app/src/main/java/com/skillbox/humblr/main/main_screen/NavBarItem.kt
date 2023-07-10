package com.skillbox.humblr.main.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun NavBarItem(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_profile,
    id: Int = 0,
    selectedId: Int = 0,
    onClick: () -> Unit = {}
) {

    val isSelected = id == selectedId
    val selectedColor = MaterialTheme.colorScheme.onPrimary
    val unselectedColor = MaterialTheme.colorScheme.inversePrimary

    Icon(
        modifier = modifier
            .clickable {
                if (!isSelected) onClick()
            }
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(vertical = 20.dp),
        painter = painterResource(id = icon),
        contentDescription = null,
        tint = if (isSelected) selectedColor else unselectedColor
    )
}

@ElementPreview
@Composable
fun NavBarItemPreview() {
    AppTheme {
        NavBarItem(
            modifier = Modifier.width(250.dp)
        )
    }
}