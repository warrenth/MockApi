package com.kth.mocksy.core.domain

import com.kth.mocksy.core.data.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedArticleIdsUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
) {
    operator fun invoke(): Flow<Set<String>> =
        articleRepository.getLikedArticleIds()
}
