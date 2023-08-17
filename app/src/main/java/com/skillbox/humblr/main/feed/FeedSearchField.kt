package com.skillbox.humblr.main.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skillbox.humblr.R
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium
import com.skillbox.humblr.theme.hintStyle

@Composable
fun FeedSearchField(
    onSearch: (String) -> Unit = {}
) {

    val hintText = stringResource(id = R.string.search)

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    BasicTextField(
        modifier = Modifier
            .padding(
                top = 12.dp,
                start = 12.dp,
                end = 12.dp
            ),
        value = searchText,
        onValueChange = {
            searchText = it
        },
        singleLine = true,
        textStyle = bodyMedium
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .fillMaxWidth()
                .padding(end = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        if (searchText.isNotEmpty()) {
                            onSearch(searchText)
                        }
                    }
                    .padding(all = 14.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )

            Box {
                if (searchText.isEmpty()) {
                    Text(
                        text = hintText,
                        style = hintStyle,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                it()
            }
        }
    }
}


@ElementPreview
@Composable
fun PreviewFeedSearchField() {
    AppTheme {
        FeedSearchField()
    }
}