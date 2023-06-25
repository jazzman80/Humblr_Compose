package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme

@Composable
fun LoadStateIndicator(
    modifier: Modifier,
    loadState: LoadState,
    onRefreshButton: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {

        val (indicator, errorMessage, refreshButton) = createRefs()

        if (loadState is Loading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(indicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        if (loadState is Error) {
            Text(
                text = stringResource(id = R.string.network_error),
                modifier = Modifier.constrainAs(errorMessage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    onRefreshButton()
                },
                modifier = Modifier.constrainAs(refreshButton) {
                    top.linkTo(errorMessage.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text(text = stringResource(id = R.string.refresh))
            }
        }

    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewLoadStateIndicator() {
    AppTheme {
        //LoadStateIndicator()
    }
}