plugins {
    id("mockapi.android.library")
    id("mockapi.android.library.compose")
    id("mockapi.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("mockapi.spotless")
}

android {
    namespace = "com.kth.mockapi.core.navigation"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)
    api(libs.androidx.navigation.compose)
}
