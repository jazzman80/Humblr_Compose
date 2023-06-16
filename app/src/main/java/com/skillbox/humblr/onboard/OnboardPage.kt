package com.skillbox.humblr.onboard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.titleMedium


@Composable
fun OnboardPage(index: Int) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (image, title, message, button) = createRefs()

        Image(
            painter = painterResource(
                id = when (index) {
                    0 -> R.drawable.img_onboard_1
                    1 -> R.drawable.img_onboard_2
                    else -> R.drawable.img_onboard_3
                }
            ),
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(
                id = when (index) {
                    0 -> R.string.onboard_title_1
                    1 -> R.string.onboard_title_2
                    else -> R.string.onboard_title_3
                }
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(title) {
                    bottom.linkTo(message.top, margin = 7.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colorScheme.onBackground,
            style = titleMedium
        )

        Text(
            text = stringResource(
                id = when (index) {
                    0 -> R.string.onboard_message_1
                    1 -> R.string.onboard_message_2
                    else -> R.string.onboard_message_3
                }
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(message) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colorScheme.onBackground,
            style = bodySmall
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
fun PreviewOnboardPage() {
    AppTheme {
        OnboardPage(2)
    }
}