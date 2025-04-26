package com.kth.mockapi.core.designsystem.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kth.mockapi.core.designsystem.theme.ArticleTheme

@Composable
fun BoxScope.MocksyCircularProgress() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = ArticleTheme.colors.primary
    )
}
