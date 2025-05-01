package com.kth.mocksy.core.datastore.datastore

import kotlinx.coroutines.flow.Flow

interface LikePreferencesDataStore {
    val likedArticle: Flow<Set<String>>
    suspend fun updateLikedArticle(likedArticle: Set<String>)
}