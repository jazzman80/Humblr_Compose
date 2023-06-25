package com.skillbox.humblr.main.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.skillbox.humblr.main.favorites.SharedModel

@Composable
fun ProfileScreen(
    viewModel: SharedModel = hiltViewModel()
) {

    val countState by viewModel.count.observeAsState()

    Text(
        text = "$countState",
        modifier = Modifier.fillMaxSize()
    )
}