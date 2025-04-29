package com.kth.mocksy.core.domain

import com.kth.mocksy.core.data.repository.ArticleRepository
import javax.inject.Inject

class LikeArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
){
    suspend operator fun invoke(articleId: String, liked: Boolean) =
        articleRepository.likeArticle(articleId, liked)
}
