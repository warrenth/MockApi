package com.kth.mockapi.feature.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kth.mockapi.core.data.repository.ArticlesRepository
import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.MockApiScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    repository: ArticlesRepository,
    private val navigator: AppComposeNavigator<MockApiScreen>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val article = savedStateHandle.getStateFlow<Article?>("article", null)

    fun navigateUp() {
        navigator.navigateUp()
    }
}