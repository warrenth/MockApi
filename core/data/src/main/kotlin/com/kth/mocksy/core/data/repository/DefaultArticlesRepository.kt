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

import android.util.Log
import com.kth.mocksy.core.data.mapper.toData
import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.data.api.ArticlesDispatcher
import com.kth.mocksy.core.data.api.Dispatcher
import com.kth.mocksy.core.data.api.MocksyService
import com.kth.mocksy.core.datastore.datastore.LikePreferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class DefaultArticlesRepository @Inject constructor(
    private val mocksyService: MocksyService,
    private val likedDataStore: LikePreferencesDataStore,
) : ArticleRepository {

    private val likedIdsForFlow: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet()) //test
    private val likedIds: Flow<Set<String>> = likedDataStore.likedArticle

    override fun getArticles(): Flow<List<Article>> = flow {
        emit(mocksyService.fetchArticles().map { it.toData() })
    }

    override fun getLikedArticleIds(): Flow<Set<String>> {
        return likedIds.filterNotNull()
    }

    override suspend fun likeArticle(articleId: String, liked: Boolean) {
        val currentLikedArticleIds = likedIds.first()
        Log.d("TriggerIds", "likeArticle() before call - likedIds: $currentLikedArticleIds")
        likedDataStore.updateLikedArticle(
            buildSet {
                addAll(currentLikedArticleIds)
                if(liked) add(articleId) else remove(articleId)
            }
        )
        Log.d("TriggerIds", "likeArticle() after call - likedIds: $currentLikedArticleIds")
    }

    fun likeArticleFlowTest(articleId: String, liked: Boolean) {
        Log.d("TriggerIds", "likeArticle() before call - likedIds: ${likedIdsForFlow.value}")
        likedIdsForFlow.update { currentIds ->
            if (liked) {
                currentIds + articleId // 좋아요 추가
            } else {
                currentIds - articleId // 좋아요 삭제
            }
        }
    }
}
