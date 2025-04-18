package com.kth.mockapi.feature.article

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpalette.palette.graphics.Palette
import com.kth.mockapi.core.designsystem.component.ArticleCircularProgress
import com.kth.mockapi.core.designsystem.component.ArticleTopAppBar
import com.kth.mockapi.core.designsystem.component.ArticlesSharedElement
import com.kth.mockapi.core.designsystem.component.sharedTransitionForArticle
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.model.MockUtils.mockArticle
import com.kth.mockapi.core.navigation.boundsTransform
import com.skydoves.compose.effects.RememberedEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.PalettePlugin
import com.skydoves.landscapist.palette.rememberPaletteState
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

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
                ArticleCircularProgress()
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
    var palette by rememberPaletteState()
    val backgroundBrush by palette.paletteBackgroundBrush()

    val context = LocalContext.current

    DetailsAppBar(
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
private fun DetailsAppBar(
    article: Article,
    backgroundBrush: Brush,
    navigateUp: () -> Unit,
) {
    ArticleTopAppBar(
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
            .sharedTransitionForArticle(
                scope = this@SharedTransitionScope,
                articleKey = article.title,
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp),
            imageModel = { article.cover },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            component = rememberImageComponent {
                +ShimmerPlugin(
                    Shimmer.Resonate(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                    ),
                )

                if (!LocalInspectionMode.current) {
                    +PalettePlugin(
                        imageModel = article.cover,
                        useCache = true,
                        paletteLoadedListener = { onPaletteLoaded.invoke(it) },
                    )
                }
            },
            previewPlaceholder = painterResource(
                id = com.kth.mockapi.core.designsystem.R.drawable.placeholder,
            ),
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
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 22.sp,
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
