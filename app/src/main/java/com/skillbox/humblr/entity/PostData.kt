package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName
import java.time.Instant

data class PostData(
    val id: String = "",
    val title: String = "",
    val author: String = "",

    @SerializedName("num_comments")
    val numComments: Int = 0,

    val thumbnail: String = "",
    val selftext: String = "",
    val saved: Boolean = false,
    val name: String = "",
    val created: Long = 0L,
    val subreddit: String = ""
)

class PostDataPreviewProvider : PreviewParameterProvider<PostData> {

    override val values = sequenceOf(
        PostData(
            id = "id",
            title = "Короткий или длинный пост: как выбрать оптимальный объём текста под свою аудиторию?",
            author = "КисКисХ2",
            numComments = 123,
            thumbnail = "https://i.pinimg.com/originals/27/af/37/27af37c4e92fcfbcfcfe89c82fbf5c87.jpg",
            selftext = "Считается, что короткие посты работают гораздо лучше: отнимают меньше времени у пользователя на прочтение. С другой стороны, качественные объёмные публикации не менее действенные, потому что позволяют автору раскрыть тему и дать ответы на поставленные вопросы. Сделать однозначный вывод по оптимальной длине текста попросту невозможно.\n" +
                    "\n" +
                    "И пусть по этому вопросу есть масса зарубежных исследований, но в данном случае они не совсем уместны. Причины: интересы аудитории за рубежом и в России во многом различаются, ни одна статистика даже с большой выборкой не даёт точного ответа. Поэтому в статье будут аргументы в пользу каждого из форматов и выводы.",
            saved = false,
            name = "",
            created = Instant.now().epochSecond - 23154,
            subreddit = "Длинные посты"
        ),
        PostData(
            id = "id",
            title = "Короткий или длинный пост?",
            author = "ПользовательССамымДлиннымНикНэймом",
            numComments = 28547,
            thumbnail = "",
            selftext = "Считается, что короткие посты работают гораздо лучше: отнимают меньше времени у пользователя на прочтение. С другой стороны, качественные объёмные публикации не менее действенные.",
            saved = true,
            name = "",
            created = Instant.now().epochSecond - 230154,
            subreddit = "Длинные посты"
        )
    )
}