package com.kth.mockapi.core.data.repository

import com.kth.mockapi.core.model.Article
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun fetchArticles(): Flow<ApiResponse<List<Article>>>
}