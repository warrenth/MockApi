package com.kth.mockapi.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kth.mockapi.core.designsystem.R
import com.kth.mockapi.core.designsystem.theme.ArticleTheme

@Composable
fun ArticleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                color = ArticleTheme.colors.absoluteWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Color.Transparent
        ),
    )
}

@Preview
@Composable
private fun CatArticlesAppBarPreview() {
    ArticleTheme {
        ArticleTopAppBar(modifier = Modifier.background(ArticleTheme.colors.primary))
    }
}