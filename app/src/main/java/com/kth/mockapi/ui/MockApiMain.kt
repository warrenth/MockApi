package com.kth.mockapi.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.kth.mockapi.core.designsystem.theme.ArticleTheme
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.RouteScreen
import com.kth.mockapi.navigation.MockApiNavHost

/**
 * AppComposeNavigator : navigation 명령을 보내고 받을 중앙 객체, 멀티모듈 환경 공유
 * rememberNavController : Navigation 상태를 관리할 Controller, 화면 전환 사
 */
@Composable
fun MockApiMain(composeNavigator: AppComposeNavigator<RouteScreen>) {
    ArticleTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            //viewmodel 이나 다른 비-UI 계층에 요청한 "화면 전환 명령) 을 NavControll가 실제 수행
            composeNavigator.handleNavigationCommands(navHostController)
        }
        //Navigation Graph 설정
        MockApiNavHost(navHostController = navHostController)
    }
}
