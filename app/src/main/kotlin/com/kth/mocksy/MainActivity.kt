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
package com.kth.mocksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import com.kth.mocksy.core.designsystem.util.LocalWindowSizeClass
import com.kth.mocksy.core.navigation.AppComposeNavigator
import com.kth.mocksy.core.navigation.MocksyScreen
import com.kth.mocksy.ui.MockApiMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterial3WindowSizeClassApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<MocksyScreen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)

            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass,
            ) {
                MockApiMain(composeNavigator = composeNavigator)
            }
        }
    }
}
