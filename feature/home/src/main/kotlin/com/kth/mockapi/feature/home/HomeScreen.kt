package com.kth.mockapi.feature.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
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
    onNavigateToDetails: (Article) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState == HomeUiState.Loading) {
            ArticleCircularProgress()
        } else if (uiState is HomeUiState.Success) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = uiState.articles,
                    key = { it.title }
                ) { article ->
                    ArticleCard(
                        article = article,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
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
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(6.dp))
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
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            component = rememberImageComponent {
                +ShimmerPlugin(
                    Shimmer.Resonate(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                    ),
                )
            },
            previewPlaceholder = painterResource(
                id = com.kth.mockapi.core.designsystem.R.drawable.placeholder,
            ),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.65f))
                .padding(12.dp),
            text = article.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

