package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.humblr.database.CommentTypeConverter
import java.time.Instant

@Entity(tableName = "comment")
@TypeConverters(CommentTypeConverter::class)
data class CommentDto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "author")
    val author: String? = null,

    @ColumnInfo(name = "created")
    val created: Long? = null,

    @ColumnInfo(name = "body")
    val body: String? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "replies")
    val replies: Listing? = null,

    @ColumnInfo(name = "saved")
    val saved: Boolean? = false
)

class CommentPreviewProvider : PreviewParameterProvider<CommentDto> {

    override val values = sequenceOf(
        CommentDto(
            author = "АлексейН",
            created = Instant.now().epochSecond - 587L,
            body = "Горы, море, прекрасный вид – ребята, мы в Дагестане! У каждого должны быть 15 минут борьбы с режимом в день",
            replies = Listing(
                data = ListingData(
                    after = null,
                    children = listOf(
                        Thing(
                            ThingData(
                                author = "ПользовательССамымДлиннымНикнеймом",
                                created = Instant.now().epochSecond - 12478L,
                                body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!"
                            )
                        )
                    )
                )
            )
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
