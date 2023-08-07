package com.skillbox.humblr.main.profile.me

import MainScreen
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skillbox.humblr.R
import com.skillbox.humblr.auth.AuthActivity
import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.entity.MeDto
import com.skillbox.humblr.entity.MePreviewProvider
import com.skillbox.humblr.main.profile.friend_list.FriendListScreen
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.preview.SystemUI
import com.skillbox.humblr.theme.AppTheme
import com.skillbox.humblr.theme.bodySmall
import com.skillbox.humblr.theme.labelLarge
import com.skillbox.humblr.theme.titleLarge
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import com.skydoves.landscapist.placeholder.thumbnail.ThumbnailPlugin
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class MeScreen : AndroidScreen() {

    @Composable
    override fun Content() {

        val repository = koinInject<Repository>()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        MeScreenContent(
            me = repository.me,
            navigateToFriendListScreen = {
                navigator.push(FriendListScreen())
            },
            clearSaved = {
                scope.launch {
                    repository.clearDownloaded()
                }
                Toast.makeText(
                    context,
                    R.string.delete_saved_message,
                    Toast.LENGTH_SHORT
                ).show()
            },
            exit = {
                scope.launch {
                    repository.exit()
                    context.startActivity(Intent(context, AuthActivity::class.java))
                }
            }
        )
    }

}

@Composable
fun MeScreenContent(
    me: MeDto? = null,
    navigateToFriendListScreen: () -> Unit = {},
    clearSaved: () -> Unit = {},
    exit: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .clip(shape = shapes.medium)
                    .background(color = colorScheme.surface)
                    .padding(all = 20.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CoilImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp),
                    imageModel = { me?.iconImg },
                    previewPlaceholder = R.drawable.sample_avatar,
                    component = rememberImageComponent {
                        +ThumbnailPlugin()
                        +PlaceholderPlugin.Loading(
                            painterResource(id = R.drawable.avatar_placeholder)
                        )
                    },
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clip(CircleShape)
                        .background(color = colorScheme.inversePrimary)
                        .padding(
                            horizontal = 22.dp,
                            vertical = 2.dp
                        ),
                    text = me?.name ?: "",
                    style = titleLarge,
                    color = colorScheme.onBackground
                )

                Text(
                    modifier = Modifier.padding(
                        top = 12.dp
                    ),
                    text = stringResource(id = R.string.total_karma) + ": "
                            + me?.totalKarma.toString(),
                    style = bodySmall,
                    color = colorScheme.onBackground
                )

                Text(
                    text = stringResource(id = R.string.comment_karma) + ": "
                            + me?.commentKarma.toString(),
                    style = bodySmall,
                    color = colorScheme.onBackground
                )
            }


            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clip(CircleShape)
                    .clickable { navigateToFriendListScreen() }
                    .background(color = colorScheme.primary)
                    .padding(vertical = 12.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.friend_list),
                style = labelLarge,
                color = colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { clearSaved() }
                    .background(color = colorScheme.primary)
                    .padding(vertical = 12.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.clear_saved),
                style = labelLarge,
                color = colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .clip(CircleShape)
                    .clickable { exit() }
                    .background(color = colorScheme.error)
                    .padding(vertical = 12.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.exit),
                style = labelLarge,
                color = colorScheme.onError,
                textAlign = TextAlign.Center
            )

        }
    }
}


@ElementPreview
@Composable
fun PreviewMeScreenContent(
    @PreviewParameter(MePreviewProvider::class) me: MeDto
) {
    AppTheme {
        SystemUI {
            MainScreen {
                MeScreenContent(
                    me = me
                )
            }
        }
    }
}