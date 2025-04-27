package com.kth.mocksy.core.designsystem.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.palette.graphics.Palette

/**
 * Palette 상태를 remember로 보관
 */
@Composable
fun rememberImagePalette(): MutableState<Palette?> {
    return remember { mutableStateOf<Palette?>(null) }
}

/**
 * Palette → 단색 Brush (투명도 포함)
 * @param alpha 투명도 (기본 0.5f)
 */
fun Palette?.toSolidColorBrush(alpha: Float = 0.5f): Brush {
    val baseColor = this?.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Black
    return SolidColor(baseColor.copy(alpha = alpha))
}
