package com.skillbox.humblr.main.core.vote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun VoteButton(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheck: () -> Unit = {},
    onUncheck: () -> Unit = {}
) {

    Icon(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable {
                if (isChecked) onUncheck() else onCheck()
            }
            .padding(all = 6.dp),
        painter = painterResource(
            id = if (isChecked) R.drawable.ic_vote_checked else R.drawable.ic_vote_unchecked
        ),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.outline
    )
}

@ElementPreview
@Composable
fun PreviewVoteButton() {
    AppTheme {
        VoteButton()
    }
}