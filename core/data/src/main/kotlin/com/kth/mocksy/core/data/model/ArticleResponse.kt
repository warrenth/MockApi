package com.kth.mocksy.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("description") val description: String,
    @SerialName("author") val author: String,
    @SerialName("date") val date: String,
    @SerialName("cover") val cover: String,
)
