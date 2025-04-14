package com.kth.mockapi.core.navigation

import com.kth.mockapi.core.model.Article
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed class RouteScreen {

    @Serializable
    data object Home: RouteScreen()

    @Serializable
    data class Detail(val article: Article) : RouteScreen() {
        companion object {
            val typeMap = mapOf(typeOf<Detail>() to CatArticlesType)
        }
    }
}