package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kth.mockapi.core.navigation.MockApiScreen

@Composable
fun MockApiNavHost(navHostController: NavHostController) {
    /**
     * 전체 composable 에 SharedTransition scope 을 제공
     * scope 만 제공한 것이지 직접 사용한 composable 만 적용 됨
     */
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = MockApiScreen.Home,
        ) {
            mockApiNavigation(this@SharedTransitionLayout)

        }
    }
}