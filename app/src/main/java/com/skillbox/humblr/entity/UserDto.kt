package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: String = "",
    val name: String = "",

    @SerializedName("icon_img")
    val iconImg: String = "",

    @SerializedName("is_friend")
    val isFriend: Boolean = false
)

class UserListPreviewProvider : PreviewParameterProvider<List<UserDto>> {

    override val values = sequenceOf(
        listOf(
            UserDto(
                id = "0",
                name = "Иванов"
            ),
            UserDto(
                id = "1",
                name = "Петров"
            ),
            UserDto(
                id = "2",
                name = "Сидоров"
            ),
            UserDto(
                id = "3",
                name = "Пупкин"
            ),
            UserDto(
                id = "4",
                name = "Сюткин"
            ),
            UserDto(
                id = "5",
                name = "Иванов"
            ),
            UserDto(
                id = "6",
                name = "Петров"
            ),
            UserDto(
                id = "7",
                name = "Сидоров"
            ),
            UserDto(
                id = "8",
                name = "Пупкин"
            ),
            UserDto(
                id = "9",
                name = "Сюткин"
            ),
        )
    )
}