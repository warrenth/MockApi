package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kth.mockapi.core.navigation.RouteScreen

@Composable
fun MockApiNavHost(navHostController: NavHostController) {
    // Motion, Animation 의 context 를 제공
    SharedTransitionLayout {
        NavHost(    //Navigation의 시작점, Composable route 를 연결
            navController = navHostController,
            startDestination = RouteScreen.Home,
        ) {
            mockApiNavigation(this@SharedTransitionLayout)
        }
    }
}