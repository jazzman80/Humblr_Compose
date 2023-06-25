package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.titleMedium

@Composable
fun TopBar(
    onNavIcon: () -> Unit,
    titleText: String,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .defaultMinSize(minHeight = 60.dp)
    ) {
        val (navIcon, title) = createRefs()

        IconButton(
            onClick = { onNavIcon() },
            modifier = Modifier
                .constrainAs(navIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = titleText,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(navIcon.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            style = titleMedium,
            maxLines = 2
        )


    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewTopBar() {
    AppTheme {
        TopBar({}, "fvdfg", Modifier.fillMaxWidth())
    }
}