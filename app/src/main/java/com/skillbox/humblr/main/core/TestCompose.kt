package com.skillbox.humblr.main.core

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun TestCompose() {

    val isClicked = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    animateFloatAsState(
                        targetValue = if (isClicked.value) 150f else 20f
                    ).value
                )

            )
            .clickable { isClicked.value = !isClicked.value }
            .size(
                animateDpAsState(
                    if (isClicked.value) 150.dp else 70.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ).value
            )
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

    }
}

@ElementPreview
@Composable
fun PreviewTest() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TestCompose()
        }
    }
}