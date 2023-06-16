package com.skillbox.humblr.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.skillbox.humblr.onboard.OnboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, OnboardActivity::class.java)

        startActivity(intent)
        finish()
    }

}