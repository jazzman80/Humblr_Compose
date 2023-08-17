package com.skillbox.humblr.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.skillbox.humblr.auth.AuthActivity
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.main.MainActivity
import com.skillbox.humblr.onboard.OnboardActivity
import org.koin.android.ext.android.inject

class StartActivity : ComponentActivity() {

    //    @Inject
//    lateinit var repository: Repository
    val repository: Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent: Intent = if (repository.accessToken.isNotEmpty()) {
            Intent(this, MainActivity::class.java)
        } else if (repository.isOnboardDone) {
            Intent(this, AuthActivity::class.java)
        } else {
            Intent(this, OnboardActivity::class.java)
        }

        startActivity(intent)
        finish()
    }

}