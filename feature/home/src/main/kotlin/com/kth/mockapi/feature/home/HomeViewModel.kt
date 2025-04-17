package com.kth.mockapi.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.mockapi.core.data.repository.ArticlesRepository
import com.kth.mockapi.core.navigation.AppComposeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ArticlesRepository,
    private val navigator: AppComposeNavigator<>
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.fetchArticles()
        .mapLatest { response ->
            response.fold(
                onSuccess = { HomeUiState.Success(it) },
                onFailure = { HomeUiState.Error(it) },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading,
        )

    fun navigateToDetails(article: Article) {
        navigator.navigate(CatArticlesScreen.CatArticle(article))
    }
}

@Stable
sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(val articles: List<Article>) : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
