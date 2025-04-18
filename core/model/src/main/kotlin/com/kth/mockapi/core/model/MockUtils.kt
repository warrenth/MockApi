package com.kth.mockapi.core.model

object MockUtils {

    val mockArticle: Article
        get() = Article(
            title = "Cat Titles",
            content = "Cat Content",
            description = "Cat description",
            author = "Cat Author",
            date = "Cat Date",
            cover = "Cat Cover",
        )
}
