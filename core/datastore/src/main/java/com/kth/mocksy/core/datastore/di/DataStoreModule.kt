package com.kth.mocksy.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val LIKE_DATA_STORE_NAME = "LIKE_PREFERENCES"

    private val Context.likeDataStore by preferencesDataStore(LIKE_DATA_STORE_NAME)

    @Provides
    @Singleton
    fun provideLikeDataStore(
        @ApplicationContext context: Context
    ) : DataStore<Preferences> = context.likeDataStore
}