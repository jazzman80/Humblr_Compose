package com.skillbox.humblr.main.favorites

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.skillbox.humblr.theme.AppTheme

@Composable
fun FavoritesScreen(
    viewModel: SharedModel = hiltViewModel()
) {

    val countState = viewModel.count.observeAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "${countState.value}"
        )
        Button(onClick = { viewModel.addCount() }) {
            Text(text = "+1")
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
fun PreviewFavoritesScreen() {
    AppTheme {
        FavoritesScreen()
    }
}