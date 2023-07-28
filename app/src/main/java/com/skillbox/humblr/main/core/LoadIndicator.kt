package com.skillbox.humblr.main.core

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.theme.AppTheme

@Composable
fun LoadIndicator(
    loadingState: LoadingState? = LoadingState.LOADING,
    onRefresh: () -> Unit = {}
) {
    if (loadingState == LoadingState.LOADING) {
        CircularProgressIndicator()
    } else if (loadingState == LoadingState.ERROR) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    id = R.string.network_error
                )
            )
            Button(
                onClick = {
                    onRefresh()
                }
            ) {
                Text(
                    text = stringResource(
                        id = R.string.refresh
                    )
                )
            }
        }


    }
}


@Preview
@Composable
fun PreviewLoadIndicator() {
    AppTheme {
        LoadIndicator(
            loadingState = LoadingState.ERROR
        )
    }
}