package com.skillbox.humblr.main.profile.friend_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.skillbox.humblr.entity.UserDto
import com.skillbox.humblr.entity.UserListPreviewProvider
import com.skillbox.humblr.fake_data.usernameTestList
import com.skillbox.humblr.preview.ElementPreview
import com.skillbox.humblr.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FriendList(
    items: List<String>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(),
        verticalArrangement = Arrangement.spacedBy(11.dp),
        horizontalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        items(
            count = items.size
        ) {
            if (LocalInspectionMode.current) {
                FriendListItemContent(items[it])
            } else {
                FriendListItem(items[it])
            }
        }
    }
}

@ElementPreview
@Composable
fun PreviewFriendList(
    @PreviewParameter(UserListPreviewProvider::class) userList: List<UserDto>
) {
    AppTheme {

        val flow = MutableStateFlow(PagingData.from(userList))
        val lazyPagingItems = flow.collectAsLazyPagingItems()

        FriendList(
            items = usernameTestList
        )
    }
}