package com.kth.mockapi.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kth.mockapi.core.navigation.MockApiScreen
import com.kth.mockapi.feature.article.DetailScreen
import com.kth.mockapi.feature.home.HomeScreen

/**
 * NavGraphBuilder에 Home, Detail 경로 등록됨
 * 이 화면들은 모두 SharedTransitionLayout 아래에 있어서
 * 각 화면 간 자연스러운 애니메이션 전환이 가능하고
 *
 * NavGraphBuilder : NavHost 안에 route 를 등록할 때 쓰는 DSL 빌더
 * SharedTransitionScope : 화면 간 전환 시 공통된 에니메이션/트랜지션을 연결 해줌
 * AnimationVisibilityScope : 화면 전환 에니메이션에세 각 컴포넌트의 애니메이션 동작 범위를 정의
 *
 */
fun NavGraphBuilder.mockApiNavigation(sharedTransitionScope: SharedTransitionScope) {
    //sharedElement()를 Modifier에 쓸 수 있게 해주는 스코프. composable scope와 무관
    with(sharedTransitionScope) {
        /**
         *  sharedTransitionScope 안에서 composable{} 를 쓰고 있기 때문에
         *  composable 은 내부적으로 AnimationVisibilityScope 를 생성. (에니메이션 컨텍스트)를 의미함
         *  this 는 화면이 등장하거나 사라질 때 사용할 전환 애니메이션 정보가 담긴 스코프를 넘겨줌.
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