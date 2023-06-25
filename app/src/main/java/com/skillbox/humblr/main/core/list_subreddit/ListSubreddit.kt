package com.skillbox.humblr.main.core.list_subreddit

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.skillbox.humblr.R
import com.skillbox.humblr.entity.Subreddit
import com.skillbox.humblr.theme.AppTheme

@Composable
fun ListSubreddit(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Subreddit>,
    onRefreshButton: () -> Unit,
    onSubscribe: (Boolean, String) -> Unit
) {

    val listState = rememberLazyListState()

    ConstraintLayout(
        modifier = modifier
    ) {

        val (list, indicator, errorMessage, refreshButton) = createRefs()

        LazyColumn(
            state = listState,
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {

            items(
                count = pagingItems.itemCount,
                key = {
                    pagingItems[it]?.data?.id ?: ""
                }
            ) {
                if (pagingItems[it] != null) {
                    ItemSubreddit(
                        item = pagingItems[it]!!,
                        onSubscribe = { isSubscribed ->
                            onSubscribe(isSubscribed, pagingItems[it]!!.data.name)
                        }
                    )
                } else {
                    //ItemSubredditPlaceholder()
                }
            }
        }

        if (pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0) {
            Text(
                text = stringResource(id = R.string.nothing_found),
                modifier = Modifier.constrainAs(errorMessage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center
            )
        }

        if (pagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(indicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        if (pagingItems.loadState.refresh is LoadState.Error) {
            Text(
                text = stringResource(id = R.string.network_error),
                modifier = Modifier.constrainAs(errorMessage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    onRefreshButton()
                },
                modifier = Modifier.constrainAs(refreshButton) {
                    top.linkTo(errorMessage.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text(text = stringResource(id = R.string.refresh))
            }
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
fun PreviewListSubreddit() {
    AppTheme {
        //ListSubreddit(Modifier, )
    }
}