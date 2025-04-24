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
package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kth.mockapi.core.navigation.MockApiScreen
import com.kth.mockapi.feature.article.DetailScreen
import com.kth.mockapi.feature.home.HomeScreen

/**
 * NavGraphBuilder 에 Home, Detail 경로 등록됨
 * 이 화면은 모두 SharedTransitionLayout 아래에 있어서
 * 각 화면 간 자연스러운 애니메이션 전환이 가능하고
 *
 * NavGraphBuilder : NavHost 안에 route 를 등록할 때 쓰는 DSL 빌더
 * SharedTransitionScope : 화면 간 전환 시 공통된 에니메이션/트랜지션을 연결 해줌
 * AnimationVisibilityScope : 화면 전환 에니메이션에세 각 컴포넌트의 애니메이션 동작 범위를 정의
 *
 */
fun NavGraphBuilder.mockApiNavigation(sharedTransitionScope: SharedTransitionScope) {
    with(sharedTransitionScope) {
        /**
         *  sharedTransitionScope 안에서 composable{} 를 쓰고 있기 때문에
         *  composable 은 내부적으로 AnimationVisibilityScope 를 생성
         *  this 로 SharedTransition 을 사용할 수 있게 된다.
         */
        composable<MockApiScreen.Home> {
            HomeScreen(animatedVisibilityScope = this)
        }

        composable<MockApiScreen.Detail>(
            typeMap = MockApiScreen.Detail.typeMap,
        ) {
            DetailScreen(animatedVisibilityScope = this)
        }
    }
}
