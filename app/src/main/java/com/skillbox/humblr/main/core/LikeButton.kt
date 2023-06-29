package com.skillbox.humblr.main.core

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.Visibility.Companion.Visible
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall

@Composable
fun LikeButton(
    initState: Boolean,
    initLikes: Int? = null,
    modifier: Modifier
) {

    var isLiked by rememberSaveable { mutableStateOf(initState) }
    var likes by rememberSaveable { mutableStateOf(initLikes) }

    IconButton(
        onClick = {
            if (likes != null) {
                likes = if (isLiked) likes!! - 1 else likes!! + 1
            }

            isLiked = !isLiked
        },
        modifier = modifier.wrapContentWidth()
    ) {
        ConstraintLayout {

            val (count, heart) = createRefs()

            Text(
                text = if (likes != null) likes.toString() else "",
                color = MaterialTheme.colorScheme.outline,
                style = bodySmall,
                modifier = Modifier
                    .constrainAs(count) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        visibility = if (likes != null) Visible else Gone
                    }
            )

            Icon(
                painter = if (isLiked) painterResource(id = R.drawable.ic_liked)
                else painterResource(id = R.drawable.ic_unliked),
                contentDescription = "like button",
                tint = if (isLiked) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .constrainAs(heart) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(count.end, margin = 2.dp)
                    }
            )

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
fun PreviewLikeButton() {
    AppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LikeButton(
                modifier = Modifier,
                initState = false,
                initLikes = 100
            )
        }
    }
}