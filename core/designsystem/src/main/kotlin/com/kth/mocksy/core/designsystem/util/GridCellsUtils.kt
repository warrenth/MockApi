package com.kth.mocksy.core.designsystem.util

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass> {
    error("WindowSizeClass not provided")
}

@Composable
fun rememberResponsiveGridCells(
    compactColumns: Int = 2,
    mediumMinSize: Dp = 160.dp,
    expandedMinSize: Dp = 200.dp,
): GridCells {

    val widthSizeClass = LocalWindowSizeClass.current.widthSizeClass

    return when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> GridCells.Fixed(compactColumns)
        WindowWidthSizeClass.Medium -> GridCells.Adaptive(mediumMinSize)
        WindowWidthSizeClass.Expanded -> GridCells.Adaptive(expandedMinSize)
        else -> GridCells.Fixed(compactColumns)
    }
}
