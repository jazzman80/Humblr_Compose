import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skillbox.humblr.main.NavBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

@Composable
fun MainScreen(
    content: @Composable (modifier: Modifier) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (content, navBar) = createRefs()

        content(
            modifier = Modifier
                .constrainAs(content)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(navBar.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        NavBar(
            Modifier.constrainAs(navBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@ElementPreview
@Composable
fun MainScreenPreview() {
    AppTheme {
        SystemUI {
            MainScreen {

            }
        }
    }
}