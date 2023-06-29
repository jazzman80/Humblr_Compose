package com.skillbox.humblr.main.single_post

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.skillbox.humblr.theme.AppTheme

@Composable
fun SinglePostScreen() {
    ConstraintLayout {
        

    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewSinglePostScreen() {
    AppTheme {
        SinglePostScreen()
    }
}