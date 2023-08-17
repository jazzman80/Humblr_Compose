package com.skillbox.humblr.fake_data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.skillbox.humblr.entity.CommentDto
import java.time.Instant

class CommentListPreviewProvider : PreviewParameterProvider<List<CommentDto>> {

    override val values = sequenceOf(
        listOf(

            CommentDto(
                id = "0",
                author = "АлексейН",
                created = Instant.now().epochSecond - 587L,
                body = "Горы, море, прекрасный вид – ребята, мы в Дагестане! У каждого должны быть 15 минут борьбы с режимом в день",
//                replies = Listing(
//                    data = ListingData(
//                        after = null,
//                        children = listOf(
//                            Thing(
//                                ThingData(
//                                    author = "ПользовательССамымДлиннымНикнеймом",
//                                    created = Instant.now().epochSecond - 12478L,
//                                    body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!"
//                                )
//                            )
//                        )
//                    )
//                )
            ),

            CommentDto(
                id = "1",
                author = "ПользовательССамымДлиннымНикнеймом",
                created = Instant.now().epochSecond - 12478L,
                body = "Шесть или пять полов напридумывали. Трансформеры, транс… Я даже не понимаю, что это такое!"
            ),

            CommentDto(
                id = "2",
                author = "Вениамин Б.",
                created = Instant.now().epochSecond - 58774L,
                body = "Даже обезьянам хорошо, когда есть бабушки."
            ),

            CommentDto(
                id = "3",
                author = "Тираторе",
                created = Instant.now().epochSecond - 124778L,
                body = "Нет в природе красивых и не красивых.Есть понятие -мы все на одной планете."
            ),

            CommentDto(
                id = "4",
                author = "ЛЮДМИЛА МОРОЗОВА",
                created = Instant.now().epochSecond - 1847587L,
                body = "Малыши, конечно, прелесть прелестная, но и взрослые обезьянки благодаря выразительным глазам вполне себе ! Жаль ,что даже при таких уникальных возможностях организма жизнь животинок сытой и беспечной не назовешь..."
            ),
        )
    )
}