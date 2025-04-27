/*
 * Copyright (c) 2025 MockApi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kth.mocksy.core.data.repository

import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.network.ArticlesDispatcher
import com.kth.mocksy.core.network.Dispatcher
import com.kth.mocksy.core.network.service.ArticlesService
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class ArticlesRepositoryImpl @Inject constructor(
    private val articlesService: ArticlesService,
    @Dispatcher(ArticlesDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : ArticlesRepository {
    override fun fetchArticles(): Flow<ApiResponse<List<Article>>> = flow {
        val response = articlesService.fetchArticles().suspendOnSuccess { data }
        emit(response)
    }.flowOn(ioDispatcher)
}
