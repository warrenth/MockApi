/*
 * Copyright (c) 2025 MockApi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kth.mocksy.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription

/**
 * Compose 앱에서 ViewModel이나 다른 계층에서 화면 전환을 컨트롤 할 수 있음
 *
 * 일반적인 화면 이동
 * composeNavigator.navigate(RouteScreen.Detail(article))
 * 이전 화면으로 값 전달
 * composeNavigator.navigateBackWithResult("key", result, RouteScreen.Home)
 * 그냥 뒤로 가기
 * composeNavigator.navigateUp()
 */
abstract class Navigator {
    // "화면 이동하라는 명령" 을 담은 파이프 : NavigationCommand
    // extraBufferCapacity = Int.MAX_VALUE → 이동 명령을 최대한 버퍼에 담을 수 있게 함
    val navigationCommands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = Int.MAX_VALUE)

    // 현재 사용 중인 NavController를 보관하는 Flow
    // StateFlow라서 값이 바뀌면 실시간으로 반응 가능
    // navigation 결과를 전달할 때 필요함 (예: 화면 A → B 갔다가 결과 받고 A로 돌아오는 경우)
    val navControllerFlow = MutableStateFlow<NavController?>(null)

    // "뒤로 가기" 명령을 navigationCommands에 담아 보냄
    // 그럼 나중에 collect() 하는 곳에서 이걸 읽고 실제로 뒤로 감
    fun navigateUp() {
        navigationCommands.tryEmit(NavigationCommand.NavigateUp)
    }
}

/**
 * 이 클래스는 Navigator를 상속받아서 "실제로 어떤 경로로 이동할지" 정의함
 * 예: navigate(RouteScreen.Detail(article))
 */
abstract class AppComposeNavigator<T : Any> : Navigator() {
    // 라우트로 이동할 때 사용 (예: navigate(Home))
    abstract fun navigate(route: T, optionsBuilder: (NavOptionsBuilder.() -> Unit)? = null)

    // 이전 화면으로 이동하면서 값(result)을 전달 (예: "화면에서 선택된 항목 전달")
    abstract fun <R> navigateBackWithResult(key: String, result: R, route: T?)

    // 특정 라우트까지 백스택을 제거하고 이동
    abstract fun popUpTo(route: T, inclusive: Boolean)

    // 전체 백스택을 비우고 새 화면으로 이동
    abstract fun navigateAndClearBackStack(route: T)

    suspend fun handleNavigationCommands(navController: NavController) {
        navigationCommands
            // collect 시작될 때 1번만 호출
            .onSubscription { this@AppComposeNavigator.navControllerFlow.value = navController }
            // collect가 "끝날 때" 1번 호출
            .onCompletion { this@AppComposeNavigator.navControllerFlow.value = null }
            // emit 될 때마다 여러 번 호출
            .collect { navController.handleComposeNavigationCommand(it) }
    }

    private fun NavController.handleComposeNavigationCommand(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is ComposeNavigationCommand.NavigateToRoute<*> -> {
                navigate(navigationCommand.route, navigationCommand.options)
            }

            NavigationCommand.NavigateUp -> navigateUp() // 뒤로가기
            /**
             * 지정한 route까지 back stack을 pop(제거) 하는 함수
             * [Home] → [List] → [Detail] → [Settings]
             *
             * popBackStack("List", inclusive = false)
             * → [Settings], [Detail] 제거 → [List]가 화면으로 돌아옴
             *
             * popBackStack("List", inclusive = true)
             * → [Settings], [Detail], [List] 모두 제거 → [Home]이 화면으로 돌아옴
             */
            is ComposeNavigationCommand.PopUpToRoute<*> -> popBackStack(
                navigationCommand.route,
                navigationCommand.inclusive,
            )

            /**
             * 화면을 닫으면서, 이전 화면에 결과값을 전달하는 용도
             * startActivityForResult 와 같은 용도
             */
            is ComposeNavigationCommand.NavigateUpWithResult<*, *> -> {
                navUpWithResult(navigationCommand)
            }
        }
    }

    private fun NavController.navUpWithResult(
        navigationCommand: ComposeNavigationCommand.NavigateUpWithResult<*, *>,
    ) {
        /**
         * 예시
         *
         * // DetailScreen
         * composeNavigator.navigateBackWithResult(
         *     key = "selectedArticle",
         *     result = article,
         *     route = RouteScreen.Home
         * )
         * // HomeSceen
         * val result = savedStateHandle.getLiveData<Article>("selectedArticle")
         *
         */

        // route가 명시되어 있으면 그 route를 기준으로 backStackEntry를 가져오고,
        // 없으면 그냥 이전 화면(previousBackStackEntry)을 가져옴
        val lastBackStackEntry = navigationCommand.route?.let {
            getBackStackEntry(it)
        } ?: previousBackStackEntry

        // navigation key, result 를 가져와서  backstackEntry에 넣어버림.
        lastBackStackEntry?.savedStateHandle?.set(
            navigationCommand.key,
            navigationCommand.result,
        )

        navigationCommand.route?.let {
            popBackStack(it, false)
        } ?: navigateUp()
    }
}
