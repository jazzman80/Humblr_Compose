package com.skillbox.humblr.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.R
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge

@Composable
fun AuthScreen(
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val (gradient, logo, title, tagline, button) = createRefs()

        val bottomGuide = createGuidelineFromBottom(0.25F)
        val taglineGuide = createGuidelineFromBottom(0.3F)
        val startGuide = createGuidelineFromStart(0.15F)
        val endGuide = createGuidelineFromEnd(0.15F)

        Box(
            modifier = Modifier
                .constrainAs(gradient) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            Color.Transparent
                        )
                    )
                )
        )

        Image(
            painter = painterResource(
                id = R.drawable.img_logo
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .constrainAs(logo){
                    top.linkTo(parent.top)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(title.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Image(
            painter = painterResource(
                id = R.drawable.logo_humblr
            ),
            contentDescription = "Humblr",
            modifier = Modifier
                .constrainAs(title){
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(tagline.top, margin = 5.dp)
                }
        )

        Text(
            text = stringResource(
                id = R.string.tagline
            ),
            modifier = Modifier
                .constrainAs(tagline){
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(taglineGuide)
                },
            style = bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )


        Button(
            onClick = onClick,
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(bottomGuide)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.value(250.dp)
                }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                modifier = Modifier.padding(all = 17.dp),
                style = labelLarge
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
fun PreviewAuthScreen() {
    AppTheme {
        AuthScreen({})
    }
}