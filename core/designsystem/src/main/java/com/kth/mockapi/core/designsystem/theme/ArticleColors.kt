package com.kth.mockapi.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.kth.mockapi.core.designsystem.R

/**
 * 앱 전체에 사용하는 색상들을 하나의 클래스로 묶음
 */
@Immutable
data class ArticleColors(
    val primary: Color,
    val background: Color,
    val backgroundLight: Color,
    val backgroundDark: Color,
    val absoluteWhite: Color,
    val absoluteBlack: Color,
    val white: Color,
    val white12: Color,
    val white56: Color,
    val white70: Color,
    val black: Color,
) {

    companion object {
        @Composable
        fun defaultDarkColors(): ArticleColors = ArticleColors(
            primary = colorResource(id = R.color.colorPrimary),
            background = colorResource(id = R.color.background_dark),
            backgroundLight = colorResource(id = R.color.background800_dark),
            backgroundDark = colorResource(id = R.color.background900_dark),
            absoluteWhite = colorResource(id = R.color.white),
            absoluteBlack = colorResource(id = R.color.black),
            white = colorResource(id = R.color.white_dark),
            white12 = colorResource(id = R.color.white_12_dark),
            white56 = colorResource(id = R.color.white_56_dark),
            white70 = colorResource(id = R.color.white_70_dark),
            black = colorResource(id = R.color.black_dark),
        )

        @Composable
        fun defaultLightColors(): ArticleColors = ArticleColors(
            primary = colorResource(id = R.color.colorPrimary),
            background = colorResource(id = R.color.background),
            backgroundLight = colorResource(id = R.color.background800),
            backgroundDark = colorResource(id = R.color.background900),
            absoluteWhite = colorResource(id = R.color.white),
            absoluteBlack = colorResource(id = R.color.black),
            white = colorResource(id = R.color.white),
            white12 = colorResource(id = R.color.white_12),
            white56 = colorResource(id = R.color.white_56),
            white70 = colorResource(id = R.color.white_70),
            black = colorResource(id = R.color.black),
        )
    }

}