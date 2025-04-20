package com.kth.mockapi.core.navigation

import com.kth.mockapi.core.model.Article
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed interface MockApiScreen {

    @Serializable
    data object Home: MockApiScreen

    @Serializable
    data class Detail(val article: Article) : MockApiScreen {
        companion object {
            val typeMap = mapOf(typeOf<Detail>() to ArticlesType)
        }
    }
}