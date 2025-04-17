package com.kth.mockapi.core.data.repository

import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.network.ArticlesDispatcher
import com.kth.mockapi.core.network.Dispatcher
import com.kth.mockapi.core.network.service.ArticlesService
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class ArticlesRepositoryImpl @Inject constructor(
    private val articlesService: ArticlesService,
    @Dispatcher(ArticlesDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : ArticlesRepository {
    override fun fetchArticles(): Flow<ApiResponse<List<Article>>> = flow {
        val response = articlesService.fetchArticles().suspendOnSuccess { data }
        emit(response)
    }.flowOn(ioDispatcher)

}