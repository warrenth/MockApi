package com.kth.mockapi.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject

class MockApiomposeNavigator @Inject constructor() : AppComposeNavigator<RouteScreen>() {

  override fun navigate(route: RouteScreen, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
    val options = optionsBuilder?.let { navOptions(it) }
    navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
  }

  override fun navigateAndClearBackStack(route: RouteScreen) {
    navigationCommands.tryEmit(
      ComposeNavigationCommand.NavigateToRoute(
        route,
        navOptions {
          popUpTo(0)
        },
      ),
    )
  }

  override fun popUpTo(route: RouteScreen, inclusive: Boolean) {
    navigationCommands.tryEmit(ComposeNavigationCommand.PopUpToRoute(route, inclusive))
  }

  override fun <R> navigateBackWithResult(key: String, result: R, route: RouteScreen?) {
    navigationCommands.tryEmit(
      ComposeNavigationCommand.NavigateUpWithResult(
        key = key,
        result = result,
        route = route,
      ),
    )
  }
}
