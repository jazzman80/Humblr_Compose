package com.skillbox.humblr.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName

data class SubredditData(
    val title: String = "",
    val id: String = "",
    val name: String = "",

    @SerializedName("public_description")
    val description: String = "",

    @SerializedName("user_is_subscriber")
    val userIsSubscriber: Boolean = false
)

class SubredditDataPreviewProvider : PreviewParameterProvider<SubredditData> {

    override val values = sequenceOf(
        SubredditData(
            title = "Длинные посты",
            description = "Считается, что короткие посты работают гораздо лучше: " +
                    "отнимают меньше времени у пользователя на прочтение. С другой " +
                    "стороны, качественные объёмные публикации не менее действенные, " +
                    "потому что позволяют автору раскрыть тему и дать ответы на " +
                    "поставленные вопросы. Сделать однозначный вывод по оптимальной " +
                    "длине текста попросту невозможно."

        ),
        SubredditData(
            title = "Иногда некоторым кажется, что если сделать очень длинное название, то от " +
                    "этого будет лучше",
            userIsSubscriber = true
        ),
        SubredditData(
            title = "Саб",
            userIsSubscriber = false
        )
    )
}

class SubredditListPreviewProvider : PreviewParameterProvider<List<Subreddit>> {

    override val values = sequenceOf(
        listOf(
            Subreddit(
                SubredditData(
                    id = "0",
                    title = "Длинные посты",
                    description = "Считается, что короткие посты работают гораздо лучше: " +
                            "отнимают меньше времени у пользователя на прочтение. С другой " +
                            "стороны, качественные объёмные публикации не менее действенные, " +
                            "потому что позволяют автору раскрыть тему и дать ответы на " +
                            "поставленные вопросы."

                )
            ),
            Subreddit(
                SubredditData(
                    id = "1",
                    title = "Иногда некоторым кажется, что если сделать очень длинное название, " +
                            "то от этого будет лучше",
                    userIsSubscriber = true
                )
            ),
            Subreddit(
                SubredditData(
                    id = "2",
                    title = "Здорово, медиа",
                    description = "Здесь говорим о здоровом отношении к онлайн-пространству, " +
                            "учимся эффективно пользоваться благами digital-среды и вместе " +
                            "ищем баланс между Интернетом и реальной жизнью",
                    userIsSubscriber = true
                )
            ),
            Subreddit(
                SubredditData(
                    id = "3",
                    title = "ЗОНГ | всё о театре",
                    description = "о театральных событиях живо, ярко и со вкусом.",
                    userIsSubscriber = false
                )
            ),
            Subreddit(
                SubredditData(
                    id = "4",
                    title = "Саб",
                    userIsSubscriber = false
                )
            )
        )
    )
}