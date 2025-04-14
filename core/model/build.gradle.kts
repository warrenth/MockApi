plugins {
    id("mockapi.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    id("mockapi.spotless")
}

android {
    namespace = "com.kth.mockapi.core.model"
}

dependencies {
    api(libs.retrofit.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)

    compileOnly(libs.compose.stable.marker)
}
