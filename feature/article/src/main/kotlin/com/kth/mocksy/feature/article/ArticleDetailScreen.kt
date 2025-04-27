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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import com.kth.mocksy.core.designsystem.component.MocksyCircularProgress
import com.kth.mocksy.core.designsystem.component.MocksyTopAppBar
import com.kth.mocksy.core.designsystem.component.sharedTransition
import com.kth.mocksy.core.designsystem.image.GlidePaletteImage
import com.kth.mocksy.core.designsystem.theme.ArticleTheme
import com.kth.mocksy.core.designsystem.theme.rememberImagePalette
import com.kth.mocksy.core.designsystem.theme.toSolidColorBrush
import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.model.MockUtils.mockArticle

@Composable
fun SharedTransitionScope.DetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    val article by viewModel.article.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        if (article == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                MocksyCircularProgress()
            }
        } else {
            ArticlesDetailContent(
                article = article!!,
                animatedVisibilityScope = animatedVisibilityScope,
                navigateUp = { viewModel.navigateUp() },
            )
        }
    }
}

@Composable
private fun SharedTransitionScope.ArticlesDetailContent(
    article: Article,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateUp: () -> Unit,
) {
    var palette by rememberImagePalette()
    val backgroundBrush = palette.toSolidColorBrush(alpha = 0.7f)

    DetailsTopAppBar(
        article = article,
        navigateUp = navigateUp,
        backgroundBrush = backgroundBrush,
    )

    DetailsHeader(
        article = article,
        animatedVisibilityScope = animatedVisibilityScope,
        onPaletteLoaded = { palette = it },
    )

    DetailsContent(article = article)
}

@Composable
private fun DetailsTopAppBar(
    article: Article,
    backgroundBrush: Brush,
    navigateUp: () -> Unit,
) {
    MocksyTopAppBar(
        modifier = Modifier.background(backgroundBrush),
        title = article.title,
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(onClick = { navigateUp.invoke() }),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = null,
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Like",
                tint = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(12.dp)
                    .clickable(onClick = {}),
            )
        }
    )
}

@Composable
private fun SharedTransitionScope.DetailsHeader(
    article: Article,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onPaletteLoaded: (Palette) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .sharedTransition(
                scope = this@SharedTransitionScope,
                key = article.title,
                animatedVisibilityScope = animatedVisibilityScope,
            ),
    ) {
        GlidePaletteImage(
            imageUrl = article.cover,
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp),
            contentScale = ContentScale.FillBounds,
            onPaletteLoaded = onPaletteLoaded,
        )
    }
}

@Composable
private fun DetailsContent(
    article: Article,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
    ) {
        Text(
            text = article.description,
            color = ArticleTheme.colors.black,
            fontSize = 26.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = article.content + article.content + article.content,
            color = ArticleTheme.colors.black,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        )
    }
}

@Preview
@Composable
private fun CatArticlesDetailContentPreview() {
    ArticleTheme {
        SharedTransitionScope {
            AnimatedVisibility(visible = true, label = "") {
                Column(modifier = Modifier.fillMaxSize()) {
                    ArticlesDetailContent(
                        article = mockArticle,
                        animatedVisibilityScope = this@AnimatedVisibility,
                        navigateUp = {},
                    )
                }
            }
        }
    }
}
