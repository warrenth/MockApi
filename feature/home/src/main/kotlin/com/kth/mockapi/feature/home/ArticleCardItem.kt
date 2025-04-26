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
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kth.mockapi.core.designsystem.component.sharedTransition
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.model.MockUtils.mockArticle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SharedTransitionScope.ArticleCardItem(
    article: Article,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetails: (Article) -> Unit,
    onLikeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onNavigateToDetails.invoke(article) }
            .sharedTransition(
                scope = this@SharedTransitionScope,
                key = article.title,
                animatedVisibilityScope = animatedVisibilityScope,
            ),
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = { article.cover },
            imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
        )

        // 텍스트 배경
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.45f))
                .padding(12.dp),
        ) {
            Text(
                text = article.title,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        // 좋아요 버튼
        Icon(
            imageVector = Icons.Default.FavoriteBorder, // 빈 하트 아이콘
            contentDescription = "Like",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(3.dp)
                .background(
                    Color.Black.copy(alpha = 0.4f),
                    shape = CircleShape,
                )
                .padding(8.dp)
                .clickable { onLikeClick() },
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ArticleCardPreview() {
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
