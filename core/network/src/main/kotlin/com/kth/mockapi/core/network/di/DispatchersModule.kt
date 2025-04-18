package com.kth.mockapi.core.network.di

import com.kth.mockapi.core.model.Article
import com.kth.mockapi.core.network.ArticlesDispatcher
import com.kth.mockapi.core.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    @Provides
    @Dispatcher(ArticlesDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}