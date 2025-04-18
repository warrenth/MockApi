package com.kth.mockapi.core.data.di

import com.kth.mockapi.core.data.repository.ArticlesRepository
import com.kth.mockapi.core.data.repository.ArticlesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsArticlesRepository(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticlesRepository
}