plugins {
    id("mocksy.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    id("mocksy.spotless")
}

android {
    namespace = "com.kth.mocksy.core.model"
}

dependencies {
    api(libs.retrofit.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)

    compileOnly(libs.compose.stable.marker)
}
