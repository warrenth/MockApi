package com.kth.mockapi.core.network.service

import com.kth.mockapi.core.model.Article
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ArticlesService {

    @GET("articles")
    suspend fun fetchArticles(): ApiResponse<List<Article>>
}
