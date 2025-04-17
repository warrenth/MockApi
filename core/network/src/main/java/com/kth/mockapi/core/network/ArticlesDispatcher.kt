package com.kth.mockapi.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(
    val articleDispatcher: ArticlesDispatcher
)

enum class ArticlesDispatcher {
    IO,
}