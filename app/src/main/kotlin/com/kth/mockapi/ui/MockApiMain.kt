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
package com.kth.mockapi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.MockApiScreen
import com.kth.mockapi.navigation.MockApiNavHost

/**
 * AppComposeNavigator : navigation 명령을 보내고 받을 중앙 객체, 멀티모듈 환경 공유
 * rememberNavController : Navigation 상태를 관리할 Controller, 화면 전환 사
 */
@Composable
fun MockApiMain(composeNavigator: AppComposeNavigator<MockApiScreen>) {
    ArticleTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            // viewmodel 이나 다른 비-UI 계층에 요청한 "화면 전환 명령) 을 NavControll가 실제 수행
            composeNavigator.handleNavigationCommands(navHostController)
        }
        // Navigation Graph 설정
        MockApiNavHost(navHostController = navHostController)
    }
}
