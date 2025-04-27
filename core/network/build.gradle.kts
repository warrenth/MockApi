plugins {
    id("mocksy.android.library")
    id("mocksy.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("mocksy.spotless")
}

android {
    namespace = "com.kth.mocksy.core.network"
}

dependencies {
    implementation(projects.core.model)

    api(libs.retrofit)
    api(libs.sandwich)
    api(libs.okhttp.logging)

    api(libs.retrofit.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)
}
