package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName

data class MeDto(
    val name: String,

    @SerializedName("icon_img")
    val iconImg: String,

    @SerializedName("total_karma")
    val totalKarma: Int,

    @SerializedName("comment_karma")
    val commentKarma: Int

)

class MePreviewProvider : PreviewParameterProvider<MeDto> {

    override val values = sequenceOf(
        MeDto(
            name = "Мега_Мозг",
            iconImg = "",
            totalKarma = 10,
            commentKarma = 125
        )
    )
}
