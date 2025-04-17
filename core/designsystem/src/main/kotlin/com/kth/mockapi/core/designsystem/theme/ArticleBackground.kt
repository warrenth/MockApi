package com.kth.mockapi.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kth.mockapi.core.designsystem.R


/**
 * CompositionLocal 을 사용해 배경 테마를 하위 콤포저블에 전달
 * 기본값은 Unspecified 이므로 반드시 상위에서 값을 내려줘야 한다.
 */
val LocalBackgroundTheme: ProvidableCompositionLocal<ArticleBackground> = staticCompositionLocalOf { ArticleBackground() }

/**
 * 배경 전용 테마 구성
 * dark 모드 여부에 따라 배경 색상 결정
 */

@Immutable
data class ArticleBackground(
    val color: Color = Color.Unspecified,  // 기본 배경 색상
    val tonalElevation: Dp = Dp.Unspecified  // 그림자 높이 정보 (elevation 효과)
) {

    companion object {
        @Composable
        fun defaultBackground(darkTheme: Boolean) : ArticleBackground {
            return if (darkTheme) {
                ArticleBackground(
                    color = colorResource(id = R.color.background_dark),
                    tonalElevation = 0.dp
                )
            } else {
                ArticleBackground(
                    color = colorResource(id = R.color.background),
                    tonalElevation = 0.dp
                )
            }
        }
    }
}