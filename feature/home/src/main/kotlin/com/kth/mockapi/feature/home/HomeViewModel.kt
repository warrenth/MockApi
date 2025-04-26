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
package com.kth.mockapi.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.mockapi.core.data.repository.ArticlesRepository
import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.MockApiScreen
import com.skydoves.sandwich.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ArticlesRepository,
    private val navigator: AppComposeNavigator<MockApiScreen>,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.fetchArticles()
        .mapLatest { response ->
            response.fold(
                onSuccess = { HomeUiState.Success(it) },
                onFailure = { HomeUiState.Error(it) },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading,
        )

    fun navigateToDetails(article: Article) {
        navigator.navigate(MockApiScreen.Detail(article))
    }

    fun toggleLike() {
        
    }
}

@Stable
sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(val articles: List<Article>) : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
