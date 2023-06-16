package com.skillbox.humblr.onboard

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodyMedium
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardScreen(onSkipButtonClick: () -> Unit) {

    val pagerState = rememberPagerState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        val (topBar, pager, bottomBar) = createRefs()
        val startGuide = createGuidelineFromStart(15.dp)
        val endGuide = createGuidelineFromEnd(15.dp)
        val topGuide = createGuidelineFromTop(0.02F)
        val bottomGuide = createGuidelineFromBottom(0.1F)
        val pagerBottomGuide = createGuidelineFromBottom(0.2F)
        val pagerTopGuide = createGuidelineFromTop(0.2F)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(topGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_onboard_toolbar),
                contentDescription = "logo"
            )

            TextButton(onClick = onSkipButtonClick) {
                Text(
                    text = stringResource(id = R.string.skip),
                    style = bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        HorizontalPager(
            pageCount = 3,
            beyondBoundsPageCount = 2,
            state = pagerState,
            modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(pagerTopGuide)
                    bottom.linkTo(pagerBottomGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {
            OnboardPage(index = it)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .constrainAs(bottomBar) {
                    bottom.linkTo(bottomGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                },
        ) {
            repeat(3) {
                val coroutineScope = rememberCoroutineScope()
                DotIndicator(isSelected = it == pagerState.currentPage, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                })
            }
        }
    }

}

@Composable
fun DotIndicator(
    isSelected: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outlineVariant, CircleShape
            )
            .height(8.dp)
            .width(8.dp)
            .clickable(onClick = onClick)
    )
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewOnboardScreenLight() {
    AppTheme {
        OnboardScreen({})
    }
}