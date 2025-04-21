package com.kth.mockapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import com.kth.mockapi.core.designsystem.util.LocalWindowSizeClass
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.MockApiScreen
import com.kth.mockapi.ui.MockApiMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterial3WindowSizeClassApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<MockApiScreen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)

            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass
            ) {
                MockApiMain(composeNavigator = composeNavigator)
            }
        }
    }
}