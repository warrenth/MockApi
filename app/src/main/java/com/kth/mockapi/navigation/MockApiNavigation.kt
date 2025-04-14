package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kth.mockapi.core.navigation.RouteScreen
import com.kth.mockapi.feature.article.DetailScreen
import com.kth.mockapi.feature.home.HomeScreen

fun NavGraphBuilder.mockApiNavigation(sharedTransitionScope: SharedTransitionScope) {
    with(sharedTransitionScope) {
        composable<RouteScreen.Home> {
            HomeScreen(animatedVisibilityScope = this)
        }

        composable<RouteScreen.Detail>(
            typeMap = RouteScreen.Detail.typeMap,
        ) {
            DetailScreen(animatedVisibilityScope = this)
        }
    }
}