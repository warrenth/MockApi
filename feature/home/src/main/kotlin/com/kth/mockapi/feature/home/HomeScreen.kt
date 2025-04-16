package com.kth.mockapi.feature.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import java.awt.Color
import java.lang.reflect.Modifier

@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        MockTopBar()

        HomeContent(
            uiState = uiState,
            animatedVisibilityScope = animatedVisibilityScope
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
            MockCircularProgess()
        } else if (uiState is HomeUiState.Success) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = uiState.articles
                    key = { it.title }
                ) { article ->
                    MockCard(
                        article = article,
                        animatedVisibilityScope = animatedVisibilityScope
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.MockCard(
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
                id = R.drawable.placeholder,
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

