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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.model.MockUtils.mockArticle

class ArticleCardItemScreenshotTest {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Preview(
        showBackground = true,
    )
    @Composable
    fun ArticleCardItemPreview() {
        ArticleTheme {
            SharedTransitionScope {
                AnimatedVisibility(visible = true, label = "") {
                    ArticleCardItem(
                        article = mockArticle,
                        animatedVisibilityScope = this,
                        onNavigateToDetails = {},
                        onLikeClick = {},
                    )
                }
            }
        }
    }
}
