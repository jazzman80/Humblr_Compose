package com.skillbox.humblr.onboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.skillbox.humblr.auth.AuthActivity
import com.skillbox.humblr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                OnboardScreen(onSkipButtonClick = {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }
}