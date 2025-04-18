package com.kth.mockapi.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.mockapi.core.data.repository.ArticlesRepository
import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.navigation.AppComposeNavigator
import com.kth.mockapi.core.navigation.RouteScreen
import com.skydoves.sandwich.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ArticlesRepository,
    private val navigator: AppComposeNavigator<RouteScreen>
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
        navigator.navigate(RouteScreen.Detail(article))
    }
}

@Stable
sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(val articles: List<Article>) : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
