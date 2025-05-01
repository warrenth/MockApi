package com.kth.mocksy.core.datastore.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultLikedPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LikePreferencesDataStore {

    companion object {
        val LIKED_ARTICLE = stringSetPreferencesKey("LIKED_ARTICLE")
    }

    override val likedArticle: Flow<Set<String>> =
        dataStore.data
            .catch { emit(emptyPreferences()) }  // io 입출력간에 오류 발생시 (디스크 파일손상, 권한문제, 버전 호환성)
            .map { it[LIKED_ARTICLE] ?: emptySet() } // LIKED_ARTICLE 키가 없으면 빈 Set 반환
            .distinctUntilChanged()  // 동일한 값으로 연속 emit 될 경우 이전값과 다른 값일 때만 UI에게 전달. (set끼리 정렬순서보장하지 않지만, 동일값 체크해줌)

    override suspend fun updateLikedArticle(likedArticle: Set<String>) {
        dataStore.edit { preferences ->
            preferences[LIKED_ARTICLE] = likedArticle
        }
    }
}
