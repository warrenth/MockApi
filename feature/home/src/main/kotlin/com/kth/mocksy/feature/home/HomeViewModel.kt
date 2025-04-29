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
package com.kth.mocksy.feature.home

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.mocksy.core.domain.GetArticlesUseCase
import com.kth.mocksy.core.domain.GetLikedArticleIdsUseCase
import com.kth.mocksy.core.domain.LikeArticleUseCase
import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.navigation.AppComposeNavigator
import com.kth.mocksy.core.navigation.MocksyScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getArticlesUseCase: GetArticlesUseCase,
    getLikedArticleIdsUseCase: GetLikedArticleIdsUseCase,
    private val likeArticleUseCase: LikeArticleUseCase,
    private val navigator: AppComposeNavigator<MocksyScreen>,
) : ViewModel() {


    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        combine(
            getArticlesUseCase(),
            getLikedArticleIdsUseCase(),
        ) { articles, likedIds ->
            Log.d("TriggerIds", "combine call - likedIds: $likedIds, articles: $articles ")
            HomeUiState.Success(
                articles = articles
                    .map { article ->
                        article.copy(liked = likedIds.contains(article.id))
                    }
                    .toPersistentList()
            )
        }
            .catch { throwable ->
                Log.d("TriggerIds", "combine catch $throwable")
                _errorFlow.emit(throwable)
            }
            .onEach { combinedUiState ->
                _uiState.value = combinedUiState
            }
            .launchIn(viewModelScope)
    }

    fun navigateToDetails(article: Article) {
        navigator.navigate(MocksyScreen.Detail(article))
    }

    fun toggleLike(article: Article) {
        viewModelScope.launch {
            val reversedLike = !article.liked
            Log.d("TriggerIds", "toggleLike call - reversedLike: $reversedLike")
            likeArticleUseCase(article.id, reversedLike)
        }
    }
}

@Stable
sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(val articles: List<Article>) : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
