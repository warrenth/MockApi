package com.kth.mockapi.core.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Immutable
@Parcelize
@Serializable
data class Article(
    val title: String,
    val content: String,
    val description: String,
    val author: String,
    val date: String,
    val cover: String,
) : Parcelable