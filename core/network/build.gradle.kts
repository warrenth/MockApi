plugins {
    id("mockapi.android.library")
    id("mockapi.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("mockapi.spotless")
}

android {
    namespace = "com.kth.mockapi.core.network"
}

dependencies {
    implementation(projects.core.model)

    api(libs.retrofit)
    api(libs.sandwich)
    api(libs.okhttp.logging)

    api(libs.retrofit.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)
}
