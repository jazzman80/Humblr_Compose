package com.skillbox.humblr.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.skillbox.humblr.R
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.main.MainActivity
import com.skillbox.humblr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// Управляет авторизацией пользователя
// Переход на активити основного приложения

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    @Inject
    lateinit var repository: Repository
    private lateinit var errorToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AuthScreen { authorize() }
            }
        }
        errorToast = Toast.makeText(
            this,
            R.string.auth_error,
            Toast.LENGTH_LONG
        )
        errorToast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)

        handleDeepLink(intent)
    }

    private fun authorize() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            repository.composeUrl()
        )
        startActivity(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        if (intent.action != Intent.ACTION_VIEW) return
        val deepLinkUrl = intent.data ?: return
        if (deepLinkUrl.queryParameterNames.contains("error")) {
            errorToast.show()
        } else if (deepLinkUrl.queryParameterNames.contains("code")) {
            val authCode = deepLinkUrl.getQueryParameter("code")
                ?: return

            lifecycleScope.launch {
                val response = repository.getAccessToken(authCode)

                if (response.isSuccessful) {
                    val exitIntent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(exitIntent)
                    finish()

                } else {
                    errorToast.show()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleDeepLink(it) }
    }

}