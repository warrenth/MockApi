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
package com.kth.mocksy.feature.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.mocksy.core.domain.GetLikedArticleIdsUseCase
import com.kth.mocksy.core.domain.LikeArticleUseCase
import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.navigation.AppComposeNavigator
import com.kth.mocksy.core.navigation.MocksyScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val likeArticleUseCase: LikeArticleUseCase,
    private val getLikedArticleIdsUseCase: GetLikedArticleIdsUseCase,
    private val navigator: AppComposeNavigator<MocksyScreen>,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // article 은 repository 에 cache 하는 방법 추후 개선
    private val _article = savedStateHandle.getStateFlow<Article?>("article", null)
    val article: StateFlow<Article?> = _article

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked

    init {
        viewModelScope.launch {
            combine(
                _article.filterNotNull(),
                getLikedArticleIdsUseCase()
            ) { article, likedIds ->
                _isLiked.value = likedIds.contains(article.id)
            }.collect {

            }
        }
    }

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun toggleLike() {
        viewModelScope.launch {
            val currentArticle = _article.value ?: return@launch
            val newLikedState = !_isLiked.value
            _isLiked.value = newLikedState
            likeArticleUseCase(currentArticle.id, newLikedState)
        }
    }
}