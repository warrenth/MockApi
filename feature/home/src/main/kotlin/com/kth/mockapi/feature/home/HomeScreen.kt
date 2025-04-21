package com.kth.mockapi.feature.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kth.mockapi.core.designsystem.component.ArticleCircularProgress
import com.kth.mockapi.core.designsystem.component.ArticleTopAppBar
import com.kth.mockapi.core.designsystem.component.sharedTransitionForArticle
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.model.Article
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // composable life cycle
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(bottom = 50.dp)) {
        ArticleTopAppBar(modifier = Modifier.background(ArticleTheme.colors.primary))

        HomeContent(
            uiState = uiState,
            animatedVisibilityScope = animatedVisibilityScope,
            onNavigateToDetails = { viewModel.navigateToDetails(it) }
        )
    }
}

@Composable
private fun SharedTransitionScope.HomeContent(
    uiState : HomeUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetails: (Article) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is HomeUiState.Loading -> {
                ArticleCircularProgress()
            }
            is HomeUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(
                        items = uiState.articles,
                        key = { it.title },
                    ) { article ->
                        ArticleCard(
                            article = article,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onNavigateToDetails = onNavigateToDetails,
                        )
                    }
                }
            }
            is HomeUiState.Error -> {
                Text(
                    text = "Error",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.ArticleCard(
    article: Article,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetails: (Article) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable { onNavigateToDetails.invoke(article) }
            .sharedTransitionForArticle(
                scope = this@SharedTransitionScope,
                articleKey = article.title,
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = { article.cover },
            imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.45f))
                .padding(12.dp),
            text = article.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Thin
        )
    }
}

