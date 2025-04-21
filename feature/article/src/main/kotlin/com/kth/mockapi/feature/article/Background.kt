package com.kth.mockapi.feature.article

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.kth.mockapi.core.designsystem.theme.ArticleTheme

@Composable
internal fun Palette?.paletteBackgroundBrush(): State<Brush> {
    val defaultBackground = ArticleTheme.colors.background
    return remember(this) {
        derivedStateOf {
            val light = this?.lightVibrantSwatch?.rgb
            val domain = this?.dominantSwatch?.rgb
            if (domain != null) {
                val domainColor = Color(domain)
                if (light != null) {
                    val lightColor = Color(light)
                    val gradient = arrayOf(
                        0.0f to domainColor,
                        1f to lightColor,
                    )
                    Brush.verticalGradient(colorStops = gradient)
                } else {
                    Brush.linearGradient(colors = listOf(domainColor, domainColor))
                }
            } else {
                Brush.linearGradient(colors = listOf(defaultBackground, defaultBackground))
            }
        }
    }
}
