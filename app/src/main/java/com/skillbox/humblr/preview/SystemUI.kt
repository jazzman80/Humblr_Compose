package com.skillbox.humblr.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall

@Composable
fun SystemUI(
    content: @Composable (modifier: Modifier) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (systemBar, content) = createRefs()

        Row(
            modifier = Modifier
                .constrainAs(systemBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "14:52",
                style = bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_system_wifi),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_system_signal),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_system_battery),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        content(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(systemBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

    }
}

@ElementPreview
@Composable
fun SystemUIPreview() {
    AppTheme {
        SystemUI {}
    }
}