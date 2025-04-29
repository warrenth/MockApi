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
package com.kth.mocksy.feature.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kth.mocksy.core.model.Article
import com.kth.mocksy.core.navigation.AppComposeNavigator
import com.kth.mocksy.core.navigation.MocksyScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val navigator: AppComposeNavigator<MocksyScreen>,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val article = savedStateHandle.getStateFlow<Article?>("article", null)

    fun navigateUp() {
        navigator.navigateUp()
    }
}
