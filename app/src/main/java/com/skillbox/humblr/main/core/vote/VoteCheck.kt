package com.skillbox.humblr.main.core.vote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.main.core.Counter
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme

@Composable
fun VoteCheck(
    votes: Int = 0,
    onVote: (Int) -> Unit = {},
    likes: Boolean? = null
) {

    val initUserVote: Int
    val initUpChecked: Boolean
    val initDownChecked: Boolean

    if (likes == null) {
        initUserVote = 0
        initUpChecked = false
        initDownChecked = false
    } else if (likes) {
        initUserVote = 1
        initUpChecked = true
        initDownChecked = false
    } else {
        initUserVote = -1
        initUpChecked = false
        initDownChecked = true
    }

    var userVote by rememberSaveable(initUserVote) {
        mutableStateOf(initUserVote)
    }

    var isUpChecked by rememberSaveable(initUpChecked) {
        mutableStateOf(initUpChecked)
    }

    var isDownChecked by rememberSaveable(initDownChecked) {
        mutableStateOf(initDownChecked)
    }

    Row(
        modifier = Modifier
            .defaultMinSize(minWidth = 80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VoteButton(
            modifier = Modifier
                .rotate(180f),
            isChecked = isUpChecked,
            onCheck = {
                isUpChecked = true
                isDownChecked = false
                userVote = 1
                onVote(userVote)
            },
            onUncheck = {
                isUpChecked = false
                userVote = 0
                onVote(userVote)
            }
        )

        Counter(
            value = userVote + votes
        )

        VoteButton(
            isChecked = isDownChecked,
            onCheck = {
                isDownChecked = true
                isUpChecked = false
                userVote = -1
                onVote(userVote)
            },
            onUncheck = {
                isDownChecked = false
                userVote = 0
                onVote(userVote)
            }
        )
    }
}

@ElementPreview
@Composable
fun PreviewVoteCheck() {
    AppTheme {
        VoteCheck()
    }
}