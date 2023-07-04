package com.skillbox.humblr.main.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.androidx.AndroidScreen
import com.skillbox.humblr.theme.AppTheme

class ProfileScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        Text(
            text = "Profile Screen",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }

    @Preview(
        name = "Light Mode", showBackground = true
    )
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
    )
    @Composable
    fun Preview() {
        AppTheme {
            Content()
        }
    }


}