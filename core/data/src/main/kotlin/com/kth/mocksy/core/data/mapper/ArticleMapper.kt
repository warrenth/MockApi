package com.kth.mocksy.core.data.mapper

import com.kth.mocksy.core.data.model.ArticleResponse
import com.kth.mocksy.core.model.Article

internal fun ArticleResponse.toData(): Article {
    return Article(
        id = id,
        title = title,
        content = content,
        description = description,
        author = author,
        date = date,
        cover = cover,
        liked = false
    )
}

