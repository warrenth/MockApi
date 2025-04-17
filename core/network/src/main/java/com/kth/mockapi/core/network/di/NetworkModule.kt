package com.kth.mockapi.core.network.di

import com.kth.mockapi.core.network.service.ArticlesService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideArticlesRetrofit(networkJson: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                "https://67fc66eb1f8b41c8168661ea.mockapi.io/",
            )
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleService(
        retrofit: Retrofit,
    ): ArticlesService = retrofit.create()
}