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
    val subreddit: String = "",
    val preview: PostPreview? = null,
    val permalink: String = ""
)

class PostDataPreviewProvider : PreviewParameterProvider<PostData> {

    override val values = sequenceOf(
        PostData(
            id = "id",
            title = "Короткий или длинный пост: как выбрать оптимальный объём текста под " +
                    "свою аудиторию?",
            author = "КисКисХ2",
            numComments = 123,
            thumbnail = "https://i.pinimg.com/originals/27/af/37/27af37c4e92fcfbcfcfe89c82fbf5c87.jpg",
            selftext = "Считается, что короткие посты работают гораздо лучше: " +
                    "отнимают меньше времени у пользователя на прочтение. С другой " +
                    "стороны, качественные объёмные публикации не менее действенные, " +
                    "потому что позволяют автору раскрыть тему и дать ответы на " +
                    "поставленные вопросы. Сделать однозначный вывод по оптимальной " +
                    "длине текста попросту невозможно. И пусть по этому вопросу есть " +
                    "масса зарубежных исследований, но в данном случае они не совсем " +
                    "уместны. Причины: интересы аудитории за рубежом и в России во " +
                    "многом различаются, ни одна статистика даже с большой выборкой не " +
                    "даёт точного ответа. Поэтому в статье будут аргументы в пользу " +
                    "каждого из форматов и выводы.",
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
            selftext = "Считается, что короткие посты работают гораздо лучше: отнимают " +
                    "меньше времени у пользователя на прочтение. С другой стороны, " +
                    "качественные объёмные публикации не менее действенные.",
            saved = true,
            name = "",
            created = Instant.now().epochSecond - 230154,
            subreddit = "Длинные посты"
        )
    )
}

class PostListPreviewProvider : PreviewParameterProvider<List<Post>> {

    override val values = sequenceOf(
        listOf(
            Post(
                PostData(
                    id = "1",
                    title = "Короткий или длинный пост: как выбрать оптимальный объём текста " +
                            "под свою аудиторию?",
                    author = "КисКисХ2",
                    numComments = 123,
                    thumbnail = "https://i.pinimg.com/originals/27/af/37/27af37c4e92fcfbcfcfe89c82fbf5c87.jpg",
                    selftext = "Считается, что короткие посты работают гораздо лучше: " +
                            "отнимают меньше времени у пользователя на прочтение. С другой " +
                            "стороны, качественные объёмные публикации не менее действенные, " +
                            "потому что позволяют автору раскрыть тему и дать ответы на " +
                            "поставленные вопросы. Сделать однозначный вывод по оптимальной " +
                            "длине текста попросту невозможно. И пусть по этому вопросу есть " +
                            "масса зарубежных исследований, но в данном случае они не совсем " +
                            "уместны. Причины: интересы аудитории за рубежом и в России во " +
                            "многом различаются, ни одна статистика даже с большой выборкой не " +
                            "даёт точного ответа. Поэтому в статье будут аргументы в пользу " +
                            "каждого из форматов и выводы.",
                    saved = false,
                    name = "",
                    created = Instant.now().epochSecond - 23154,
                    subreddit = "Длинные посты"
                )
            ),
            Post(
                PostData(
                    id = "2",
                    title = "Короткий или длинный пост?",
                    author = "ПользовательССамымДлиннымНикНэймом",
                    numComments = 28547,
                    thumbnail = "",
                    selftext = "Считается, что короткие посты работают гораздо лучше: " +
                            "отнимают меньше времени у пользователя на прочтение. С другой " +
                            "стороны, качественные объёмные публикации не менее действенные.",
                    saved = true,
                    name = "",
                    created = Instant.now().epochSecond - 230154,
                    subreddit = "Длинные посты"
                )
            ),
            Post(
                PostData(
                    id = "3",
                    title = "Художник Денис Жбанков и его монстры",
                    author = "TheDogsDream",
                    numComments = 1447,
                    thumbnail = "https://i.pinimg.com/originals/27/af/37/27af37c4e92fcfbcfcfe89c82fbf5c87.jpg",
                    selftext = "Денис Жбанков - российский художник-фрилансер, создающий арты " +
                            "для компьютерных и настольных игр. Работы Жбанкова особенно " +
                            "выделяются среди других качеством освещения, подбором ракурсов " +
                            "и противопоставлением пропорций и размеров.",
                    saved = false,
                    name = "",
                    created = Instant.now().epochSecond - 154,
                    subreddit = "Длинные посты"
                )
            ),
            Post(
                PostData(
                    id = "4",
                    title = "10 примеров наихудшего использования компьютерной графики в кино за последние года",
                    author = "KinoGeek",
                    numComments = 847,
                    thumbnail = "https://i.pinimg.com/originals/27/af/37/27af37c4e92fcfbcfcfe89c82fbf5c87.jpg",
                    selftext = "Давайте будем честны: в последнее время Голливуд теряет былую " +
                            "хватку в сфере визуальных эффектов. Каждый второй выходящий " +
                            "крупный блокбастер от именитых студий, снятый более чем за " +
                            "200 миллионов долларов, выглядит как дешёвая и блеклая " +
                            "подделка, спецэффекты которой будто бы пилили на коленке прямо во " +
                            "время пре-продакшена. В этой статье я бы хотел вспомнить " +
                            "примеры недавних голливудских фильмов с самой отвратительной, " +
                            "мультяшной и посредственной графикой, которые выходили за " +
                            "последние шесть лет. Ленты киновселенной Marvel я тут " +
                            "избегу (отдельную статью про них писал вот тут), однако в " +
                            "рейтинг попала парочка кинокомиксов разного уровня качества.",
                    saved = true,
                    name = "",
                    created = Instant.now().epochSecond - 11054,
                    subreddit = "Длинные посты"
                )
            ),
        )
    )
}