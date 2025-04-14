package com.kth.mockapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.RouteScreen
import com.kth.mockapi.ui.MockApiMain
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<RouteScreen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MockApiMain(composeNavigator)
        }
    }
}