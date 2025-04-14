package com.kth.mockapi.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.kth.mockapi.core.navigation.RouteScreen
import com.kth.mockapi.navigation.MockApiNavHost

@Composable
fun MockApiMain(composeNavigator: AppComposeNavigator<RouteScreen>) {
    MaterialTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        MockApiNavHost(navHostController = navHostController)
    }
}
