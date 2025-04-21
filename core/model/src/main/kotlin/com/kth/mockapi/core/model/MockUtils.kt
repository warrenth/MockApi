package com.kth.mockapi.core.model

object MockUtils {

    val mockArticle: Article
        get() = Article(
            title = "고양이의 낮잠 비밀",
            content = "고양이는 하루 평균 12~16시간을 잠으로 보냅니다. 충분한 수면은 고양이 건강에 매우 중요합니다.",
            description = "고양이의 수면 습관을 알아봅니다.",
            author = "Cat Lover",
            date = "2025-04-10",
            cover = "https://cdn2.thecatapi.com/images/MTY3ODIyMQ.jpg"
        )
}
