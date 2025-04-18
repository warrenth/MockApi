@file:OptIn(ExperimentalComposeUiApi::class)

package com.kth.mockapi.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

/**
 * 앱 전체에서 사용할 색상 팔레트를 전달하는 CompositionLocal
 */
private val LocalColors = compositionLocalOf<ArticleColors> {
    error("No colors provided")
}

/**
 * App 전체에 사용할 테마 Wrapper 함수
 * 여기서 설정한 테마는 하위 모든 Composable 에 영향을 미침
 */
@Composable
fun ArticleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 시스템 테마 감지 (true → 다크모드)
    colors: ArticleColors = if (darkTheme) {
        ArticleColors.defaultDarkColors()
    } else {
        ArticleColors.defaultLightColors()
    },
    background: ArticleBackground = ArticleBackground.defaultBackground(darkTheme),
    content: @Composable () -> Unit,
) {
    /**
     * Compose 트리의 content 블록 안에서, LocalColors.current를 호출하면 colors 값을 주겠다
     * 마찬가지로 LocalBackgroundTheme.current는 background 값을 가지게 하겠다
     *
     * CompositionLocalProvider(
     *     LocalColors provides CatArticleColors(...),
     *     LocalBackgroundTheme provides CatArticlesBackground(...)
     * )
     */
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalBackgroundTheme provides background
    ) {
        // Box는 테마의 배경을 적용한 레이아웃 컨테이너
        Box(
            modifier = Modifier
                .background(background.color)
                .semantics { testTagsAsResourceId = true },
        ) {
            content() // 실제 앱 콘텐츠 들어가는 부분
        }
    }
}

object ArticleTheme {
    val colors: ArticleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val background: ArticleBackground
        @Composable
        @ReadOnlyComposable
        get() = LocalBackgroundTheme.current
}