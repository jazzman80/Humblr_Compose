package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import java.time.Instant

data class CommentDto(
    val id: String = "",
    val name: String = "",
    val author: String? = null,
    val created: Long? = null,
    val body: String? = null
)

class CommentPreviewProvider : PreviewParameterProvider<CommentDto> {

    override val values = sequenceOf(
        CommentDto(
            author = "АлексейН",
            created = Instant.now().epochSecond - 587L,
            body = "Горы, море, прекрасный вид – ребята, мы в Дагестане! У каждого должны быть 15 минут борьбы с режимом в день"
        ),
        CommentDto(
            author = "ПользовательССамымДлиннымНикнеймом",
            created = Instant.now().epochSecond - 12478L,
            body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!"
        ),
        CommentDto()
    )
}
