package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.humblr.database.CommentTypeConverter
import java.time.Instant

@Entity(tableName = "comments")
@TypeConverters(CommentTypeConverter::class)
data class CommentDto(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val author: String? = null,
    val created: Long? = null,
    val body: String? = null,
    val avatar: String? = null,
//    val replies: Listing? = null,
    val saved: Boolean? = null,
    val score: Int? = null,
    val likes: Boolean? = null
)

class CommentPreviewProvider : PreviewParameterProvider<CommentDto> {

    override val values = sequenceOf(
        CommentDto(
            author = "АлексейН",
            created = Instant.now().epochSecond - 587L,
            body = "Горы, море, прекрасный вид – ребята, мы в Дагестане! У каждого должны быть 15 минут борьбы с режимом в день",
//            replies = Listing(
//                data = ListingData(
//                    after = null,
//                    children = listOf(
//                        Thing(
//                            ThingData(
//                                author = "ПользовательССамымДлиннымНикнеймом",
//                                created = Instant.now().epochSecond - 12478L,
//                                body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!"
//                            )
//                        )
//                    )
//                )
//            ),
            score = 95
        ),
        CommentDto(
            author = "ПользовательССамымДлиннымНикнеймом",
            created = Instant.now().epochSecond - 12478L,
            body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!",
            saved = true
        ),
        CommentDto()
    )
}
