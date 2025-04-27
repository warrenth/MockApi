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
package com.kth.mocksy.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kth.mocksy.core.navigation.MocksyScreen

@Composable
fun MocksyNavHost(navHostController: NavHostController) {
    /**
     * 전체 composable 에 SharedTransition scope 을 제공
     * scope 만 제공한 것이지 직접 사용한 composable 만 적용 됨
     */
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = MocksyScreen.Home,
        ) {
            mocksyNavigation(this@SharedTransitionLayout)
        }
    }
}
