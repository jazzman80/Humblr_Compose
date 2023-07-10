import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skillbox.humblr.main.main_screen.NavBar
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme

@Composable
fun MainScreen(
    selectedTabId: Int = 0,
    onTabSelect: (Int) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column {

        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .weight(1f)
        ) {
            content()
        }

        NavBar(
            selectedTabId = selectedTabId,
            onTabSelect = onTabSelect
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