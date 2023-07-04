package com.skillbox.humblr.main.feed

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.main.search.SearchScreen
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium
import com.skillbox.humblr.theme.hintStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedSearchField(
    modifier: Modifier
) {

    val navigator = LocalNavigator.currentOrThrow

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = modifier,
        shape = CircleShape,
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.search),
                style = hintStyle,
                color = MaterialTheme.colorScheme.outline
            )
        },
        textStyle = bodyMedium,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.search),
                modifier = Modifier
                    .clickable {
                        if (searchText.isNotEmpty()) {
                            navigator.push(SearchScreen(searchText))
                        }
                    }
            )
        }
    )
}


@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewFeedSearchField() {
    AppTheme {
        FeedSearchField(Modifier)
    }
}