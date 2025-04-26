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

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kth.mockapi.core.designsystem.component.MocksyCircularProgress
import com.kth.mockapi.core.designsystem.component.MocksyTopAppBar
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.designsystem.util.rememberResponsiveGridCells
import com.kth.mockapi.core.model.Article

@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues()),
    ) {
        MocksyTopAppBar(modifier = Modifier.background(ArticleTheme.colors.primary))

        HomeContent(
            uiState = uiState,
            animatedVisibilityScope = animatedVisibilityScope,
            onNavigateToDetails = { viewModel.navigateToDetails(it) },
            onLikeClick = { viewModel.toggleLike() }
        )
    }
}

@Composable
private fun SharedTransitionScope.HomeContent(
    uiState: HomeUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetails: (Article) -> Unit,
    onLikeClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is HomeUiState.Loading -> {
                MocksyCircularProgress()
            }

            is HomeUiState.Success -> {
                LazyVerticalGrid(
                    columns = rememberResponsiveGridCells(),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(6.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    items(
                        items = uiState.articles,
                        key = { it.title },
                    ) { article ->
                        ArticleCardItem(
                            article = article,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onNavigateToDetails = onNavigateToDetails,
                            onLikeClick = onLikeClick
                        )
                    }
                }
            }

            is HomeUiState.Error -> {
                Text(
                    text = "Error",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}
