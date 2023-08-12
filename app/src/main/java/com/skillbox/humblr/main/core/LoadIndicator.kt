package com.skillbox.humblr.main.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.skillbox.humblr.R
import com.skillbox.humblr.core.LoadingState
import com.skillbox.humblr.core.LoadingState.ERROR
import com.skillbox.humblr.core.LoadingState.LOADING
import com.skillbox.humblr.core.LoadingState.SUCCESS
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun LoadIndicator(
    loadingState: LoadingState = LOADING,
    onRefresh: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val repository = koinInject<Repository>()

    LoadIndicatorContent(
        loadingState = loadingState,
        onRefresh = {
            scope.launch {
                repository.refreshToken()
            }
        }
    )

}

@Composable
fun LoadIndicatorContent(
    loadingState: LoadingState = SUCCESS,
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loadingState == ERROR) {
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
        } else if (loadingState == LOADING) {
            CircularProgressIndicator()
        }
    }
}


@Preview
@Composable
fun PreviewLoadIndicator() {
    AppTheme {
        LoadIndicatorContent(
            loadingState = ERROR
        )
    }
}