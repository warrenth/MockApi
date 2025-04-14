package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kth.mockapi.core.navigation.RouteScreen

@Composable
fun MockApiNavHost(navHostController: NavHostController) {
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = RouteScreen.Home,
        ) {
            mockApiNavigation(this@SharedTransitionLayout)
        }
    }
}