package com.skillbox.humblr.main.feed

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.skillbox.humblr.main.core.list_subreddit.ListSubreddit
import com.skillbox.humblr.theme.AppTheme

class FeedScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<FeedViewModel>()

        val newSubs = viewModel.newSubsFlow.collectAsLazyPagingItems()
        val popularSubs = viewModel.popularSubsFlow.collectAsLazyPagingItems()

        var subs by remember {
            mutableStateOf(newSubs)
        }

        val loadState = subs.loadState.source.refresh

        val focusManager = LocalFocusManager.current

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            val (back, search, tabs, list, indicator) = createRefs()

            val startGuide = createGuidelineFromStart(0.08F)
            val endGuide = createGuidelineFromEnd(0.08F)
            val topGuide = createGuidelineFromTop(0.04F)


            Box(modifier = Modifier
                .constrainAs(back) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
            )

            FeedSearchField(
                modifier = Modifier
                    .constrainAs(search) {
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        top.linkTo(topGuide)
                        width = Dimension.fillToConstraints
                    }
            )

            FeedTabRow(
                modifier = Modifier
                    .constrainAs(tabs) {
                        top.linkTo(search.bottom)
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        width = Dimension.fillToConstraints
                    }
            ) { selectedTab ->

                subs = if (selectedTab == 0) {
                    newSubs
                } else {
                    popularSubs
                }

                subs.refresh()
                focusManager.clearFocus()
            }

            ListSubreddit(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(tabs.bottom)
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                pagingItems = subs,
                onRefreshButton = {
                    viewModel.refreshToken()
                    subs.refresh()
                },
                onSubscribe = { isSubscribed, name ->
                    viewModel.subscribe(isSubscribed, name)
                },
                onItemClick = { subTitle ->
                    //navigateToPosts(subTitle)
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
fun PreviewFeedScreen() {
    AppTheme {
        //FeedScreen()
    }
}