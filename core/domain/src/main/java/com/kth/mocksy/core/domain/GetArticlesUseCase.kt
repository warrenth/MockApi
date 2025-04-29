package com.kth.mocksy.core.domain

import com.kth.mocksy.core.data.repository.ArticleRepository
import com.kth.mocksy.core.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
){
    operator fun invoke(): Flow<List<Article>> {
        return articleRepository.getArticles()
    }
}
